package com.elixir.elixir.service.Interface;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.CartDTO;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.exceptions.CartDuplicateException;
import com.elixir.elixir.exceptions.CartNoSuchElementException;
import com.elixir.elixir.exceptions.ProductCartNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;

public interface CartService {

    public void createCart(User user) throws CartDuplicateException;

    public CartDTO getCartByUserId() throws CartNoSuchElementException;
    
    public ProductsCartDTO addProductToCart(Long product_id, int quantity) throws CartNoSuchElementException;

    public ProductsCartDTO updateProductQuantity(Long product_id, int quantity) throws CartNoSuchElementException, ProductNoSuchElementException, ProductCartNoSuchElementException;

    public Boolean removeProductFromCart(Long product_id) throws CartNoSuchElementException, ProductNoSuchElementException;

    public Boolean removeAllProductsFromCart() throws CartNoSuchElementException;

    public CartDTO convertToDto(Cart cart);

}
