package com.elixir.elixir.controllers.auth;

import com.elixir.elixir.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest { //VER DATA
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
