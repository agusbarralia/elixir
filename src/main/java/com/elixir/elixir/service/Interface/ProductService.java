package com.elixir.elixir.service.Interface;


import com.elixir.elixir.entity.Product;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;

import java.util.Optional;
import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    public Optional<Product> getProductByName(String product_name) throws ProductNoSuchElementException;

    public Product changeState(Long product_id) throws ProductNoSuchElementException;

    public Product updateProduct(Long product_id, Product newProduct) throws ProductNoSuchElementException;

    public Product createProduct(Product product);
    
}
