package com.elixir.elixir.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.exceptions.OrderNoSuchElementException;
import com.elixir.elixir.service.Interface.OrderService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "http://localhost:5173")

public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/admin")
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping
    public List<OrderDTO> getOrdersByUserId() {
        return orderService.getOrdersByUserId();
    }
    
    @GetMapping("/order/{order_id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long order_id) throws OrderNoSuchElementException {
        return ResponseEntity.ok(orderService.getOrderById(order_id));
    }

}
