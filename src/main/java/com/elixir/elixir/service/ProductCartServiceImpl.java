package com.elixir.elixir.service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.dto.ProductsCartDTO;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.service.Interface.ProductCartService;

@Service
public class ProductCartServiceImpl implements ProductCartService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCartRepository productCartRepository;

    @Override
    public ProductsCartDTO createProductCart(Long product_id, int quantity, Cart cart) {
        ProductsCart productsCart = new ProductsCart();

        Product product = productRepository.findById(product_id)
                    .orElseThrow(() -> new IllegalStateException("Producto no encontrado."));
        
        productsCart.setProduct(product);
        productsCart.setUnit_price(product.getPrice());
        productsCart.setSubtotal(product.getPrice() * quantity);
        productsCart.setQuantity(quantity);
        productsCart.setCart(cart);
        productCartRepository.save(productsCart);
        return convertToDTO(productsCart);
        
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

    public ProductsCartDTO convertToDTO(ProductsCart productsCart) {
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
