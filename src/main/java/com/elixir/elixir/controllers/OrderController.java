package com.elixir.elixir.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.exceptions.OrderNoSuchElementException;
import com.elixir.elixir.service.OrderServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;
    
    //CORREGIR
    @GetMapping("/admin/orders")
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/user_id")
    public List<OrderDTO> getOrdersByUserId(@RequestParam Long user_id) {
        return orderService.getOrdersByUserId(user_id);
    }
    
    @GetMapping("/order_id")
    public ResponseEntity<OrderDTO> getOrderById(@RequestParam Long order_id) throws OrderNoSuchElementException {
        return ResponseEntity.ok(orderService.getOrderById(order_id));
    }

}
