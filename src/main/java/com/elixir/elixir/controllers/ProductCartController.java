package com.elixir.elixir.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.elixir.elixir.service.Interface.ProductCartService;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("productcart")
public class ProductCartController {
    
    @Autowired
    private ProductCartService productCartService;

    @GetMapping("/{cart_id}")
    public List<ProductsCart> getProductCartByCartId(@PathVariable Long cart_id) {
        return productCartService.getProductCartByCartId(cart_id);
    }
    
    @PostMapping("/addtocart")
    public ResponseEntity<List<ProductsCart>> addtoCart(@RequestBody ProductsCart productsCart){
        ProductsCart result = productCartService.addtoCart(productsCart);
        return ResponseEntity.ok(productCartService.getProductCartByCartId(result.getCart().getCart_id()));

    }
    
}
