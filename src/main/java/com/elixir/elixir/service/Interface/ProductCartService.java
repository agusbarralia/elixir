package com.elixir.elixir.service.Interface;
import java.util.List;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.ProductsCartDTO;


public interface ProductCartService {
    
    public ProductsCartDTO createProductCart(Long product_id, int quantity, Cart cart)

    public List<ProductsCartDTO> convertAllToDTO(List<ProductsCart> productsCart);

    public ProductsCartDTO convertToDTO(ProductsCart productsCart);
}
