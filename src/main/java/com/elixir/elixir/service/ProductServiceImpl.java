package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.entity.Label;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductImage;
import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.entity.dto.ProductDTO;
//import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.repository.ProductImageRepository;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.service.Interface.ProductService;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductImageRepository productImageRepository;

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
    public ProductDTO createProduct(String name, String product_description, Double price, int stock,LocalDateTime date_published, boolean state,Long labelId, Long subCategoryId,Long categoryId, List<MultipartFile> images) throws ProductNoSuchElementException, java.io.IOException, SerialException, SQLException  {
        
        Optional<Product> existingProduct = productRepository.findByName(name);
        
        if (existingProduct.isPresent()) {
            throw new IllegalStateException("Ya existe un producto con el nombre " + name);
        }

        Product product = new Product();
        product.setName(name);
        product.setProduct_description(product_description);
        product.setPrice(price);
        product.setStock(stock);
        product.setDate_published(date_published);
        product.setState(state);
        
        // Configurar las relaciones
        Label label = new Label();
        label.setLabel_id(labelId);
        product.setLabel(label);
        
        SubCategory subCategory = new SubCategory();
        subCategory.setSubcategory_id(subCategoryId);
        product.setSubCategory(subCategory);
        
        Category category = new Category();
        category.setCategory_id(categoryId);
        product.setCategory(category);


        Product savedProduct = productRepository.save(product);

        for (MultipartFile image : images) {
            ProductImage productImage = new ProductImage();
            byte[] bytes = image.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes); 
            
            productImage.setImageData(blob);
            productImage.setProduct(savedProduct);
            productImageRepository.save(productImage);
        }

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
