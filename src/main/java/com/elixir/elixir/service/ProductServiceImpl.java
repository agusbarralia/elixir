package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.dto.ProductDTO;
//import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.service.Interface.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                    .map(this::convertToDTO)  // Convierte cada Product a ProductDTO
                    .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return convertToDTO(product.get());
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public ProductDTO getProductByName(String name) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            return convertToDTO(product.get());
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public ProductDTO changeState(Long product_id) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findById(product_id);
        if (product.isPresent()) {
            Product productToChange = product.get();
            productToChange.setState(!productToChange.getState());
            productRepository.save(productToChange);
            return convertToDTO(productToChange);
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public ProductDTO updateProduct(Long product_id, Product newProduct) throws ProductNoSuchElementException {
        Optional<Product> oldProduct = productRepository.findById(product_id);
        if (oldProduct.isPresent()) {
            Product updatedProduct = productRepository.save(newProduct);
            return convertToDTO(updatedProduct);
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public ProductDTO createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProduct_id());
        productDTO.setName(product.getName());
        productDTO.setProductDescription(product.getProduct_description());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setDatePublished(product.getDate_published());
        productDTO.setState(product.getState());
        productDTO.setLabelId(product.getLabel() != null ? product.getLabel().getLabel_id() : null);
        productDTO.setSubCategoryId(product.getSubCategory() != null ? product.getSubCategory().getSubcategory_id() : null);
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getCategory_id() : null);
        return productDTO;
    }

    

}
