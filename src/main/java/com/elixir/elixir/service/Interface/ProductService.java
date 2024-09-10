package com.elixir.elixir.service.Interface;

import java.time.LocalDateTime;
import com.elixir.elixir.entity.dto.ProductDTO;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;

import io.jsonwebtoken.io.IOException;

import java.sql.SQLException;

import java.util.List;
import javax.sql.rowset.serial.SerialException;


import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    public List<ProductDTO> getProducts();

    public ProductDTO getProductById(Long id) throws ProductNoSuchElementException;

    public ProductDTO getProductByName(String product_name) throws ProductNoSuchElementException;

    public ProductDTO changeState(Long product_id) throws ProductNoSuchElementException;

    public void updateProductImages(Long productId, List<MultipartFile> imagesAdd, List<Long> ImagesRemove)throws IOException, SerialException, SQLException, java.io.IOException;

    public ProductDTO updateProduct(Long id, String name, String product_description, Double price, int stock, Long varietyId, Long subCategoryId, Long categoryId) throws ProductNoSuchElementException, IOException, SerialException, SQLException, java.io.IOException;

    public ProductDTO createProduct(String name, String product_description, Double price, int stock,LocalDateTime date_published, boolean state,Long varietyId, Long subCategoryId,Long categoryId, List<MultipartFile> newImages) throws ProductNoSuchElementException, java.io.IOException, SQLException, VarietyNoSuchElementException, CategoryNoSuchElementException, SubCategoryNoSuchElementException;
    
    public void updateProductDiscount(Long productId, float discount);

    public void deleteProductByCategory(Long categoryId);

    public void deleteProductBySubCategory(Long subCategoryId);

    public void deleteProductByVariety(Long varietyId);

}
