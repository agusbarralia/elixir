package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Product;
//import com.elixir.elixir.entity.SubCategory;
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
    public Product changeState(Long product_id) throws ProductNoSuchElementException{
        Optional<Product> product = productRepository.findById(product_id);
        if (product.isPresent()) {
            Product productToChange = product.get();
            productToChange.setState(!productToChange.getState());
            productRepository.save(productToChange);
            return productToChange;
        }
        else {
            throw new ProductNoSuchElementException();
        }        
    }

    @Override
    public Product updateProduct(Long product_id, Product newProduct) throws ProductNoSuchElementException {
    Optional<Product> oldProduct = productRepository.findById(product_id);
        if (oldProduct.isPresent()) {
            Product productToUpdate = oldProduct.get();
            productToUpdate.setName(newProduct.getName());
            productToUpdate.setPrice(newProduct.getPrice());
            productToUpdate.setProduct_description(newProduct.getProduct_description());
            productToUpdate.setStock(newProduct.getStock());productToUpdate.setSubCategory(newProduct.getSubCategory());
            productToUpdate.setCategory(newProduct.getCategory());
            productToUpdate.setLabel(newProduct.getLabel());
            productRepository.save(productToUpdate);
            return productToUpdate;
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
