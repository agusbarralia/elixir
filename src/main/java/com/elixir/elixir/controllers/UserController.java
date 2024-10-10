package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserDTO;
import com.elixir.elixir.service.Interface.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:5173")

public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserDTO getUserDTO() {
    
        UserDTO userDTO = userService.getUserDTO();
        return userDTO;

    }
    
}
