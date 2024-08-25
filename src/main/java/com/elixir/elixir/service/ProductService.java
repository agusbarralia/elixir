package com.elixir.elixir.service;

import com.elixir.elixir.entity.Product;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;

import java.util.Optional;
import java.util.List;

public interface ProductService {

    public List<Product> getProducts();

    public Optional<Product> getProductByName(String product_name) throws ProductNoSuchElementException;

    public Product createProduct(String product_name, String product_description, String product_image, String product_price, String product_category, String product_label) throws ProductNoSuchElementException;

    public Product updateProduct(String product_name, String product_description, String product_image, String product_price, String product_category, String product_label) throws ProductNoSuchElementException;

    public void deleteProduct(String product_name) throws ProductNoSuchElementException;

}
