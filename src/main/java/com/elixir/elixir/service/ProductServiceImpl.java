package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Product;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.service.Interface.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();

    }
    @Override
    public Optional<Product> getProductByName(String name) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            return product;
        } else {
            throw new ProductNoSuchElementException();
        }
    }
    
    @Override
    public void deleteProduct(String id) {
        return;
    }
    
    @Override
    public Product createProduct(String name, String description, String category, String price, String quantity, String image) {
        return null;

    }
    
    @Override
    public Product updateProduct(String product_name, String product_description, String product_image,
            String product_price, String product_category, String product_label) throws ProductNoSuchElementException {
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    
    }
}
