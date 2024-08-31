package com.elixir.elixir.controllers;

import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.dto.ProductDTO;
//import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.service.Interface.ProductService;

//import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



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
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) throws ProductNoSuchElementException{
        ProductDTO result = productService.createProduct(product);
        return ResponseEntity.ok(productService.getProductById(result.getProductId()));
    }

    //VER QUE ONDA EL TIPO DE PETICION HTTP ?PUT?
    @PostMapping("admin/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product) throws ProductNoSuchElementException{
        ProductDTO result = productService.updateProduct(product.getProduct_id(),product);
        return ResponseEntity.ok(productService.getProductById(result.getProductId()));
    }

    //Se podria cambiar la URI, a delete o algo asi (cambia el state de la entidad del product)

    //VER QUE ONDA EL TIPO DE PETICION HTTP ?DELETE?
    @PostMapping("admin/changestate/{product_id}")
    public ResponseEntity<ProductDTO> changeState(@PathVariable Long product_id) throws ProductNoSuchElementException{
    return ResponseEntity.ok(productService.changeState(product_id));    
    }
    
}