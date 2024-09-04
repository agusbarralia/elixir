package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.User;
//import java.util.Optional;
//import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.dto.CartDTO;

import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;

public interface CartService {

    public CartDTO getCartByUserId() throws CartNoSuchElementException;
    
    public void createCart(User user) throws CartDuplicateException;
}
