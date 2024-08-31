package com.elixir.elixir.service.Interface;

//import java.util.Optional;
//import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.dto.CartDTO;

import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;

public interface CartService {

    public CartDTO getCartByUserId(Long userId) throws CartNoSuchElementException;
    
    public CartDTO createCart(Long userId) throws CartDuplicateException;
}
