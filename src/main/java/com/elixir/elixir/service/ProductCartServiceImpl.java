package com.elixir.elixir.service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

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

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new IllegalStateException("Producto no encontrado."));

        ProductsCart productsCart = productCartRepository.findByCartAndProduct(cart, product)
                .orElse(new ProductsCart());

        int newQuantity = productsCart.getQuantity() + quantity;

        if (newQuantity > product.getStock()) {
            throw new IllegalStateException("No hay suficiente stock.");
        }

        productsCart.setProduct(product);
        productsCart.setCart(cart);
        productsCart.setQuantity(newQuantity);
        productsCart.setUnit_price(product.getPrice() * (1 - product.getDiscount()));
        productsCart.setSubtotal(product.getPrice() * newQuantity * (1 - product.getDiscount()));
        productsCart.setDiscount(product.getDiscount());
    
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

    public void updateProductCart(ProductsCart productsCart, Product product){
        productsCart.setUnit_price(product.getPrice() * (1 - product.getDiscount()));
        productsCart.setSubtotal(product.getPrice() * productsCart.getQuantity() * (1 - product.getDiscount()));
        productsCart.setDiscount(product.getDiscount());
        productCartRepository.save(productsCart);
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
