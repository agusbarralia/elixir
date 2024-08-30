package com.elixir.elixir.service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.service.Interface.ProductCartService;

@Service
public class ProductCartServiceImpl implements ProductCartService {
    
    @Autowired
    private ProductCartRepository productCartRepository;

    @Override
    public List<ProductsCartDTO> getProductCartByCartId(Long cart_id) {
        List<ProductsCart> productsCarts = productCartRepository.findByCartId(cart_id);
        return productsCarts.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
    }

    @Override
    public ProductsCartDTO addtoCart(ProductsCart productsCart) {
        ProductsCart savedProductCart = productCartRepository.save(productsCart);
        return convertToDTO(savedProductCart);
    }

    @Override
    public void removeProduct(Long productscart_id) {
        productCartRepository.deleteById(productscart_id);
    }

    @Override
    public void removeAllProducts(Long cart_id) {
        productCartRepository.deleteByCartId(cart_id);
    }

    @Override
    public List<ProductsCartDTO> convertAllToDTO(List<ProductsCart> productsCarts) {
        if (productsCarts != null) {
            return productsCarts.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());    
        }
        return Collections.emptyList();
    }

    private ProductsCartDTO convertToDTO(ProductsCart productsCart) {
        return new ProductsCartDTO(
            productsCart.getProductscart_id(),
            productsCart.getQuantity(),
            productsCart.getUnit_price(),
            productsCart.getSubtotal(),
            productsCart.getCart().getCart_id(),
            productsCart.getProduct().getProduct_id()
        );
    }
}
