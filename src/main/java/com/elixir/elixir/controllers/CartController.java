package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.User;
//import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.service.Interface.CartService;
import com.elixir.elixir.service.Interface.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<CartDTO> getCartByUserId() throws CartNoSuchElementException{
        return ResponseEntity.ok(cartService.getCartByUserId());
    }

    @PostMapping("/add")
    public ResponseEntity<ProductsCartDTO> addToCart(@RequestParam Long productId, @RequestParam int quantity) throws CartNoSuchElementException {
        ProductsCartDTO currentProductCartDTO = cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(currentProductCartDTO);
    }
}
