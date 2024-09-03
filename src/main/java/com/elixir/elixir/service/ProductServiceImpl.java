package com.elixir.elixir.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.entity.Variety;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductImage;
import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.entity.dto.ProductDTO;
import com.elixir.elixir.entity.dto.ProductImageDTO;
import com.elixir.elixir.exceptions.ImageNoSuchElementException;
//import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.repository.CategoryRepository;
import com.elixir.elixir.repository.VarietyRepository;
import com.elixir.elixir.repository.ProductImageRepository;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.repository.SubCategoryRepository;
import com.elixir.elixir.repository.VarietyRepository;
import com.elixir.elixir.repository.CategoryRepository;

import com.elixir.elixir.service.Interface.ProductService;
import javax.sql.rowset.serial.SerialBlob;

import io.jsonwebtoken.io.IOException;

import java.sql.Blob;
import java.util.Base64;
import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
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

    public void deleteProductImage(Long productId, Long imageId) throws ProductNoSuchElementException, ImageNoSuchElementException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<ProductImage> imageOptional = product.getProductImages().stream()
                    .filter(image -> image.getId().equals(imageId))
                    .findFirst();
            if (imageOptional.isPresent()) {
                ProductImage imageToDelete = imageOptional.get();
                product.getProductImages().remove(imageToDelete);
                productRepository.save(product);
            } else {
                throw new ImageNoSuchElementException();
            }
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    public List<ProductImage> updateProductImages(Product productToUpdate, List<MultipartFile> newImages) throws IOException, SerialException, SQLException, java.io.IOException {
        List<ProductImage> updatedImages = new ArrayList<>();

        productImageRepository.deleteByProductId(productToUpdate.getProduct_id());

        for (MultipartFile image : newImages) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(productToUpdate);
            try {
                byte[] imageBytes = image.getBytes();
                Blob imageBlob = new SerialBlob(imageBytes);
                productImage.setImageData(imageBlob);
            } catch (IOException e) {
                // Manejo de la excepción
                throw new IOException("Error al leer la imagen", e);
            }
            updatedImages.add(productImage);
            productImageRepository.save(productImage);
        }
        return updatedImages;
    }

    @Override
    public ProductDTO updateProduct(Long id, String name, String product_description, Double price, int stock, Long varietyId, Long subCategoryId, Long categoryId, List<MultipartFile> newImages) throws ProductNoSuchElementException, IOException, SerialException, SQLException, java.io.IOException {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (oldProduct.isPresent()) {
            Product productToUpdate = oldProduct.get();
            System.out.println(newImages);
            List<ProductImage> imagesUpdate = updateProductImages(productToUpdate, newImages);
            productToUpdate.setName(name);
            productToUpdate.setProduct_description(product_description);
            productToUpdate.setPrice(price);
            productToUpdate.setStock(stock);
            productToUpdate.setVariety(varietyRepository.findById(varietyId).get());
            productToUpdate.setSubCategory(subCategoryRepository.findById(subCategoryId).get());
            productToUpdate.setCategory(categoryRepository.findById(categoryId).get());
            productToUpdate.setProductImages(imagesUpdate);
            Product updatedProduct = productRepository.save(productToUpdate);
            return convertToDTO(updatedProduct);
            // Actualizar las imágenes del producto
        } else {
            throw new ProductNoSuchElementException();
        }
        

    }

    @Override
    public ProductDTO createProduct(String name, String product_description, Double price, int stock,LocalDateTime date_published, boolean state,Long varietyId, Long subCategoryId,Long categoryId, List<MultipartFile> images) throws ProductNoSuchElementException, java.io.IOException, SerialException, SQLException  {
        
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
        product.setVariety(varietyRepository.findById(varietyId).get());
        

        product.setSubCategory(subCategoryRepository.findById(subCategoryId).get());
        
        product.setCategory(categoryRepository.findById(categoryId).get());


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

    public ProductDTO convertToDTO(Product product) {

        List<ProductImage> images = productImageRepository.findAllByProductId(product.getProduct_id());
        
        List<ProductImageDTO> imageDTOs = images.stream().map(image -> {
            try {
                String encodedString = Base64.getEncoder()
                    .encodeToString(image.getImageData().getBytes(1, (int) image.getImageData().length()));

                return new ProductImageDTO(encodedString);
            } catch (SQLException e) {
                // Manejo de la excepción
                throw new RuntimeException("Error encoding image to base64", e);
            }
        }).collect(Collectors.toList());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProduct_id());
        productDTO.setName(product.getName());
        productDTO.setProductDescription(product.getProduct_description());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setDatePublished(product.getDate_published());
        productDTO.setState(product.getState());
        productDTO.setVarietyId(product.getVariety() != null ? product.getVariety().getVariety_id() : null);
        productDTO.setSubCategoryId(product.getSubCategory() != null ? product.getSubCategory().getSubcategory_id() : null);
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getCategory_id() : null);
        productDTO.setImagesList(imageDTOs);
        return productDTO;
    }

    

}
