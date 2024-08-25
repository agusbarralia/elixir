package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.service.CartService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{user_id}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long user_id) throws CartNoSuchElementException{
        return ResponseEntity.ok(cartService.getCartByUserId(user_id).get());

    }

    @PostMapping("/{user_id}")
    public ResponseEntity<Cart> postMethodName(@PathVariable Long user_id) throws CartDuplicateException{
        Cart result = cartService.createCart(user_id);
        return ResponseEntity.created(URI.create("/cart/" + result.getCart_id())).body(result);
    }
    
    

}
