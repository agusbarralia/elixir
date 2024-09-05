package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.repository.UserRepository;
import com.elixir.elixir.service.Interface.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); 
        return user.getUser_id(); // Devuelve el ID del usuario
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
