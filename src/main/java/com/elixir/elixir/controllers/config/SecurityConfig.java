package com.elixir.elixir.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.elixir.elixir.entity.Role;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                            .csrf(AbstractHttpConfigurer::disable)
                            .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                            // Rutas específicas para administradores
                                            .requestMatchers("/categories/admin/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/subcategories/admin/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/varieties/admin/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/products/admin/**").hasAnyAuthority(Role.ADMIN.name())
                                            .requestMatchers("/orders/admin/**").hasAnyAuthority(Role.ADMIN.name())
            
                                            // Rutas específicas para usuarios
                                            .requestMatchers("/cart/**").hasAnyAuthority(Role.USER.name())
                                            .requestMatchers("/checkout/**").hasAnyAuthority(Role.USER.name())
                                            //.requestMatchers("/orders/**").hasAnyAuthority(Role.USER.name())
            
                                            // Rutas públicas
                                            .requestMatchers("/api/v1/auth/**").permitAll()
                                            .requestMatchers("/error/**").permitAll()
                                            .requestMatchers("/categories/**").permitAll()
                                            .requestMatchers("/subcategories/**").permitAll()
                                            .requestMatchers("/varieties/**").permitAll()
                                            .requestMatchers("/products/**").permitAll()
            
                                            // Cualquier otra solicitud debe estar autenticada
                                            .anyRequest().authenticated()
                                            )
                            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                            .authenticationProvider(authenticationProvider)
                            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }
}
