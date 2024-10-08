package com.elixir.elixir.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elixir.elixir.repository.UserRepository;
import com.elixir.elixir.controllers.auth.AuthenticationResponse;
import com.elixir.elixir.controllers.auth.AuthenticationRequest;
//import com.elixir.elixir.controllers.auth.AuthenticationController;
import com.elixir.elixir.controllers.auth.RegisterRequest;
import com.elixir.elixir.controllers.config.JwtService;
import com.elixir.elixir.entity.Role;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.exceptions.CartDuplicateException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CartServiceImpl cartService;

    public AuthenticationResponse register(RegisterRequest request) throws CartDuplicateException {
            var user = User.builder()
                            .name(request.getFirstname())
                            .last_name(request.getLastname())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .username(request.getEmail())
                            .state(true)
                            .creation_date(LocalDateTime.now())
                            .role(Role.USER)
                            .build();

            repository.save(user);
            //Le creo el carrito al usuario
            cartService.createCart(user);
            
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                            .accessToken(jwtToken)
                            .role(user.getRole())
                            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                            request.getEmail(),
                                            request.getPassword()));

            var user = repository.findByEmail(request.getEmail())
                            .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                            .accessToken(jwtToken)
                            .role(user.getRole())
                            .build();
    }



}




