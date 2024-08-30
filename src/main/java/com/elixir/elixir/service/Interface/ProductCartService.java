package com.elixir.elixir.service.Interface;
import java.util.List;

import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.ProductsCartDTO;



public interface ProductCartService {
    
    public List<ProductsCartDTO> getProductCartByCartId(Long cart_id);
    
    public ProductsCartDTO addtoCart(ProductsCart productsCart);
    
    public void removeProduct(Long productscart_id);

    public void removeAllProducts(Long cart_id);

    public List<ProductsCartDTO> convertAllToDTO(List<ProductsCart> productsCart);
}
