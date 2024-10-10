package com.elixir.elixir.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;   
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private String username;
    private String email;
    private String name;
    private String last_name;
}
