package com.elixir.elixir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.controllers.ProductCartController;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.service.Interface.ProductCartService;

@Service
public class ProductCartServiceImpl implements ProductCartService {
    
    @Autowired
    private ProductCartRepository productCartRepository;

    public List<ProductsCart> getProductCartByCartId(Long cart_id){

        return productCartRepository.findByCartId(cart_id);


    }


    public ProductsCart addtoCart(ProductsCart productsCart){

        return productCartRepository.save(productsCart);
    }

}
