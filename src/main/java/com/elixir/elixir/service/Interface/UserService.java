package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserDTO;

public interface UserService {

    public Long getCurrentUserId();

    public User getUserById(Long userId);

    public UserDTO convertToDTO(User user);

    public UserDTO userInfoUpdate(String username, String email, String name, String last_name);
}
