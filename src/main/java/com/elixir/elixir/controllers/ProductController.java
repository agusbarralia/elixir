package com.elixir.elixir.controllers;

import com.elixir.elixir.entity.Product;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.service.Interface.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
    




}