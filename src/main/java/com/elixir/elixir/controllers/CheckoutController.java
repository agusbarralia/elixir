package com.elixir.elixir.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.service.Interface.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/process")
    public ResponseEntity<Order> processCheckout(@RequestBody User user) {
        Order order = checkoutService.checkout(user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
}
