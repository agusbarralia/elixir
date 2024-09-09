package com.elixir.elixir.controllers;

//import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.dto.ProductDTO;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;
import com.elixir.elixir.service.Interface.ProductService;
//import com.fasterxml.jackson.databind.ObjectMapper;

//import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<ProductDTO> GetProducts() {
        return productService.getProducts();
    }
    
    @GetMapping("/id")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id)
        throws ProductNoSuchElementException {
            return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/name")
    public ResponseEntity<ProductDTO> getProductByName(@RequestParam String product_name)
        throws ProductNoSuchElementException {
            return ResponseEntity.ok(productService.getProductByName(product_name));
    }

    @PostMapping("admin/create")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestParam String name,
            @RequestParam String product_description,
            @RequestParam Double price,
            @RequestParam int stock,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date_published,
            @RequestParam boolean state,
            @RequestParam Long varietyId,
            @RequestParam Long subCategoryId,
            @RequestParam Long categoryId,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) 
            throws ProductNoSuchElementException, java.io.IOException, SQLException, VarietyNoSuchElementException, CategoryNoSuchElementException, SubCategoryNoSuchElementException {

        // Llamar al servicio para crear el producto
        ProductDTO result = productService.createProduct(name, product_description, price,stock, date_published, state, varietyId, subCategoryId, categoryId, images);
        
        // Devolver la respuesta
        return ResponseEntity.ok(productService.getProductById(result.getProductId()));
    }

    //VER QUE ONDA EL TIPO DE PETICION HTTP ?PUT?
    @PutMapping("admin/update/values")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String product_description,
            @RequestParam Double price,
            @RequestParam int stock,
            @RequestParam Long varietyId,
            @RequestParam Long subCategoryId,
            @RequestParam Long categoryId)
            throws ProductNoSuchElementException, java.io.IOException, SQLException {

        ProductDTO result = productService.updateProduct(id,name, product_description, price,stock, varietyId, subCategoryId, categoryId);
        return ResponseEntity.ok(productService.getProductById(result.getProductId()));
    }

    @PutMapping("admin/update/images")
    public ResponseEntity<String> updateProductImages(
        @RequestParam Long productId, 
        @RequestParam(value = "imagesAdd", required = false) List<MultipartFile> imagesAdd,
        @RequestParam(value = "imagesRemove", required = false) List<Long> imagesRemove)
            throws ProductNoSuchElementException, java.io.IOException, SQLException {
        productService.updateProductImages(productId,imagesAdd,imagesRemove);
        return ResponseEntity.ok("Imagenes actualizadas correctamente");
    }

    //Se podria cambiar la URI, a delete o algo asi (cambia el state de la entidad del product)

    //VER QUE ONDA EL TIPO DE PETICION HTTP ?DELETE?
    @PutMapping("admin/changestate")
    public ResponseEntity<ProductDTO> changeState(@RequestParam Long product_id) throws ProductNoSuchElementException{
    return ResponseEntity.ok(productService.changeState(product_id));    
    }
    
    @PutMapping("admin/update/discount")
    public ResponseEntity<String> updateDiscount(@RequestParam("product_id") Long productId, @RequestParam("discount") float discount) {
        try {
            if(discount >= 0 && discount <= 0.8){ //Nosotros lo seteamos asi
                productService.updateProductDiscount(productId, discount);
                return ResponseEntity.ok("El descuento se aplico exitosamente!");
            }else{
                return ResponseEntity.ok("Error: El descuento debe valer entre 0 y 0,8");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error al actualizar el descuento: " + e.getMessage());
        }
    }
}

