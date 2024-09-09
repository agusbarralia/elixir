package com.elixir.elixir.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.service.Interface.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
    
    @PostMapping("/process")
    public ResponseEntity<OrderDTO> processCheckout() {
        OrderDTO orderDTO = checkoutService.checkout();
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
