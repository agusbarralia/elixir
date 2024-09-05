package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.User;

public interface UserService {

    public Long getCurrentUserId();

    public User getUserById(Long userId);
}
