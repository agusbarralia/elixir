package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;

public interface CartService {

    public void createCart(User user) throws CartDuplicateException;

    public CartDTO getCartByUserId() throws CartNoSuchElementException;
    
    public ProductsCartDTO addProductToCart(Long product_id, int quantity) throws CartNoSuchElementException;

    public void removeProductFromCart(Long product_id) throws CartNoSuchElementException;

    public void removeAllProductsFromCart() throws CartNoSuchElementException;

    public CartDTO convertToDto(Cart cart);

}
