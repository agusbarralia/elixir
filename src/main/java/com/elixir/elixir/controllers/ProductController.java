package com.elixir.elixir.controllers;

import com.elixir.elixir.entity.Product;
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
    public List<Product> GetProducts() {
        return productService.getProducts();
    }
    
    @GetMapping("/{product_name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String product_name)
        throws ProductNoSuchElementException {
            return ResponseEntity.ok(productService.getProductByName(product_name).get());
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws ProductNoSuchElementException{
        Product result = productService.createProduct(product);
        return ResponseEntity.ok(productService.getProductByName(result.getName()).get());
    }
    
    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ProductNoSuchElementException{
        Product result = productService.createProduct(product);
        return ResponseEntity.ok(productService.getProductByName(result.getName()).get());
    }
   
    //Se podria cambiar la URI, a delete o algo asi (cambia el state de la entidad del product)
    @GetMapping("/changestate/{product_id}")
    public ResponseEntity<Product> changeState(@PathVariable Long product_id) throws ProductNoSuchElementException{
    return ResponseEntity.ok(productService.changeState(product_id));    
    }
    
}