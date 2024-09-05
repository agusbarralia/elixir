package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.repository.UserRepository;
import com.elixir.elixir.service.Interface.CartService;
import com.elixir.elixir.service.Interface.ProductCartService;
import com.elixir.elixir.service.Interface.UserService;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductCartService productCartService;

    @Autowired
    private ProductCartRepository productCartRepository;

    @Autowired
    private UserService userService;

    public void createCart(User user) throws CartDuplicateException {
        Optional<Cart> cart = cartRepository.findByUserId(user.getUser_id());
        if (cart.isPresent()) {
            throw new CartDuplicateException();
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
        }
    }

    public CartDTO getCartByUserId() throws CartNoSuchElementException{
        Optional<Cart> cart = cartRepository.findByUserId(userService.getCurrentUserId());
        if (cart.isPresent()) {
            return convertToDto(cart.get());
        } else {
            throw new CartNoSuchElementException();
        }
    }

    public ProductsCartDTO addProductToCart(Long product_id, int quantity) throws CartNoSuchElementException {
        Optional<Cart> cart = cartRepository.findByUserId(userService.getCurrentUserId());
        if (cart.isPresent()) {
            ProductsCartDTO productsCartDTO = productCartService.createProductCart(product_id, quantity, cart.get());
            return productsCartDTO;
        }
        else{
            throw new CartNoSuchElementException();
        }
    }

    public void removeProductFromCart(Long product_id) throws CartNoSuchElementException {
        Optional<ProductsCart> productCart = productCartRepository.findById(product_id);
        if (productCart.isPresent()) {
            productCartRepository.deleteById(product_id);
        } else {
            throw new CartNoSuchElementException();
        }
    }

    public void removeAllProductsFromCart() throws CartNoSuchElementException {
        Optional<Cart> cart = cartRepository.findByUserId(userService.getCurrentUserId());
        if (cart.isPresent()) {
            productCartRepository.deleteByCartId(cart.get().getCart_id());
        } else {
            throw new CartNoSuchElementException();
        }
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