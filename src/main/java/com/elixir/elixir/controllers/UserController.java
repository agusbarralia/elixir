package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserDTO;
import com.elixir.elixir.service.Interface.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;



    @GetMapping("/user")
    public UserDTO getUser() {
        Long userId = userService.getCurrentUserId();
        User user = userService.getUserById(userId);
        return userService.convertToDTO(user);
    }

    @PutMapping("/user")
    public UserDTO updateUserData(@RequestParam String username, @RequestParam String email, @RequestParam String name, @RequestParam String last_name) {
        return userService.userInfoUpdate(username, email, name, last_name);
    }
}
