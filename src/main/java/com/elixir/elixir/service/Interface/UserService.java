package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserDTO;

public interface UserService {

    public Long getCurrentUserId();

    public UserDTO getUserDTO();

    public User getUserById(Long userId);
}
