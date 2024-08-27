package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.User;

public interface CheckoutService {
    
    public Order checkout(User user);

}
