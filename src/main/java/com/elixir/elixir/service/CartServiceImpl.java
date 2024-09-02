package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.repository.UserRepository;
import com.elixir.elixir.service.Interface.CartService;
import com.elixir.elixir.service.Interface.ProductCartService;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductCartService productCartService;

    public CartDTO getCartByUserId(Long userId) throws CartNoSuchElementException{
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            return convertToDto(cart.get());
        } else {
            throw new CartNoSuchElementException();
        }
    }

    public CartDTO createCart(Long userId) throws CartDuplicateException {
        Optional<User> user = userRepository.findById(userId);  //ESTO ESTA MAL DEBERIA DE HACERSE EN EL REPO DE USUARIO 
        Optional<Cart> cart = cartRepository.findByUserId(userId);  //PERO NO EXISTE TODAVIA XD
        if(cart.isEmpty() && user.isPresent()){                    
            Cart newCart = new Cart();
            newCart.setUser(user.get());
            cartRepository.save(newCart);
            return convertToDto(newCart);
        }
        throw new CartDuplicateException();
    }

    public CartDTO convertToDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCart_id());
        cartDTO.setUserId(cart.getUser().getUser_id()); 
        cartDTO.setProductsCart( productCartService.convertAllToDTO(cart.getProductsCart()));

        return cartDTO;
    }
}