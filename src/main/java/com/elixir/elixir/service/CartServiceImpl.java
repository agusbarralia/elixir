package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.service.Interface.CartService;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> getCartByUserId(Long userId) throws CartNoSuchElementException{
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            return cart;
        } else {
            throw new CartNoSuchElementException();
        }
    }

    public Cart createCart(Long userId) throws CartDuplicateException {

        Optional<User> user = cartRepository.findUserById(userId);  //ESTO ESTA MAL DEBERIA DE HACERSE EN EL REPO DE USUARIO 
        Optional<Cart> cart = cartRepository.findByUserId(userId);  //PERO NO EXISTE TODAVIA
        if(cart.isEmpty() && user.isPresent()){                    
            Cart newCart = new Cart();
            newCart.setUser(user.get());
            return cartRepository.save(newCart);
        }
        throw new CartDuplicateException();
    }


}
