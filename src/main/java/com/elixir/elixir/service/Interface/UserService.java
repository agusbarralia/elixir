package com.elixir.elixir.service.Interface;

import java.util.List;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserAdminDTO;
import com.elixir.elixir.entity.dto.UserDTO;

public interface UserService {

    public Long getCurrentUserId();

    public User getUserById(Long userId);

    public UserDTO convertToDTO(User user);

    public UserDTO userInfoUpdate(String username, String email, String name, String last_name);

    public UserAdminDTO changeState(Long userId);

    public List<UserAdminDTO> getAllUsers();
}
