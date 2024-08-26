package com.elixir.elixir.service.Interface;
import java.util.List;

import com.elixir.elixir.entity.ProductsCart;


public interface ProductCartService {
    
    public List<ProductsCart> getProductCartByCartId(Long cart_id);
    
    public ProductsCart addtoCart(ProductsCart productsCart);
    
    public void deletefromCart(Long cart_id, Long product_id);
}
