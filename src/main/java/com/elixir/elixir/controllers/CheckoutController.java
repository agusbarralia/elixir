package com.elixir.elixir.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.repository.OrderRepository;
import com.elixir.elixir.service.AuthenticationService;
import com.elixir.elixir.service.OrderServiceImpl;
import com.elixir.elixir.service.UserServiceImpl;
import com.elixir.elixir.service.Interface.CheckoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired 
    UserServiceImpl userService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderService;

    
    @PostMapping("/process")
    public ResponseEntity<OrderDTO> processCheckout() {
        OrderDTO orderDTO = checkoutService.checkout();
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
