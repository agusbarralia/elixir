package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.dto.OrderDTO;

public interface CheckoutService {
    
    public OrderDTO checkout() throws IllegalStateException;

}
