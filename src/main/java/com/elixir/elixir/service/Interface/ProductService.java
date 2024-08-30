package com.elixir.elixir.service.Interface;


import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.dto.ProductDTO;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import java.util.List;

public interface ProductService {

    public List<ProductDTO> getProducts();

    public ProductDTO getProductById(Long id) throws ProductNoSuchElementException;

    public ProductDTO getProductByName(String product_name) throws ProductNoSuchElementException;

    public ProductDTO changeState(Long product_id) throws ProductNoSuchElementException;

    public ProductDTO updateProduct(Long product_id, Product newProduct) throws ProductNoSuchElementException;

    public ProductDTO createProduct(Product product);
    
}
