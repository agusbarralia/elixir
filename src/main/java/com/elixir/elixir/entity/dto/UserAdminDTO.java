package com.elixir.elixir.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminDTO {

    private String username;
    private String email;
    private String name;
    private String last_name;
    private Boolean state;
    private Long id;
    
}
