package com.elixir.elixir.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("checkout")
public class CheckoutController {
    
    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

}
