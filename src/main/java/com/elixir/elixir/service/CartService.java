package com.elixir.elixir.service;

import java.util.Optional;
import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;

public interface CartService {

    public Optional<Cart> getCartByUserId(Long userId) throws CartNoSuchElementException;
    
    public Cart createCart(Long userId) throws CartDuplicateException;
}
