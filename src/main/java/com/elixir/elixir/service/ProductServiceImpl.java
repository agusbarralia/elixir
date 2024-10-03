package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.entity.Variety;
import com.elixir.elixir.entity.Product;
import com.elixir.elixir.entity.ProductImage;
import com.elixir.elixir.entity.ProductsCart;
import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.entity.dto.ProductDTO;
import com.elixir.elixir.entity.dto.ProductImageDTO;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.exceptions.ProductNoSuchElementException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;
import com.elixir.elixir.exceptions.VarietyNoSuchElementException;
import com.elixir.elixir.repository.CategoryRepository;
import com.elixir.elixir.repository.VarietyRepository;
import com.elixir.elixir.repository.ProductImageRepository;
import com.elixir.elixir.repository.ProductRepository;
import com.elixir.elixir.repository.SubCategoryRepository;
import com.elixir.elixir.service.Interface.ProductCartService;
import com.elixir.elixir.service.Interface.ProductService;

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

    @Autowired
    private ProductCartService productCartService;

    @Override
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(Product::getState)  // Filtra productos cuyo estado sea true
                .map(this::convertToDTO)  // Convierte cada Product a ProductDTO
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent() && product.get().getState()) {
            return convertToDTO(product.get());
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public ProductDTO getProductByName(String name) throws ProductNoSuchElementException {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent() && product.get().getState()) {
            return convertToDTO(product.get());
        } else {
            throw new ProductNoSuchElementException();
        }
    }

    @Override
    public List<ProductDTO> getProductsByCategoryName (String categoryName) throws CategoryNoSuchElementException {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .filter(Product::getState)  // Filtra productos cuyo estado sea true
                .map(this::convertToDTO)  // Convierte cada Product a ProductDTO
                .collect(Collectors.toList());
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


    private void addImages(Product product, List<MultipartFile> imagesAdd) throws java.io.IOException, SerialException, SQLException{
        for (MultipartFile image : imagesAdd) {
            ProductImage productImage = new ProductImage();
            byte[] bytes = image.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            productImage.setImageData(blob);
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
    }
    
    private void removeImages(Product product, List<Long> imagesRemove){
        for (Long imageId : imagesRemove) {
            productImageRepository.deleteById(imageId);
        }

    }

    public void updateProductImages(Long productId, List<MultipartFile> imagesAdd, List<Long> imagesRemove) throws IOException, SerialException, SQLException, java.io.IOException {
        Product product = productRepository.findById(productId)
                        .orElseThrow(()-> new IllegalStateException("Producto no encontrado"));
        
        if(!imagesAdd.get(0).isEmpty()){
            addImages(product,imagesAdd);
        }

        if (!imagesRemove.isEmpty()){
            removeImages(product,imagesRemove);
        }
            
    }

    private Map<String, Object> createProductMap(Long id,String name,String product_description,Double price, int stock,Long varietyId, Long subCategoryId, Long categoryId, Product currentProduct){
        
        Map<String, Object> updates = new HashMap<>();
        if (!name.equals(currentProduct.getName())) updates.put("name", name);
        if (!product_description.equals(currentProduct.getProduct_description())) updates.put("product_description", product_description);
        if (!price.equals(currentProduct.getPrice())) updates.put("price", price);
        if (stock != currentProduct.getStock()) updates.put("stock", stock);
        if (!varietyId.equals(currentProduct.getVariety().getVariety_id())) updates.put("VarietyId", varietyId);
        if (!subCategoryId.equals(currentProduct.getSubCategory().getSubCategory_id())) updates.put("subCategoryId", subCategoryId);
        if (!categoryId.equals(currentProduct.getCategory().getCategory_id())) updates.put("categoryId", categoryId);
        
        return updates;
    }

    @Override
    public ProductDTO updateProduct(Long id,String name,String product_description,Double price, int stock,Long varietyId, Long subCategoryId, Long categoryId) throws ProductNoSuchElementException, IOException, SerialException, SQLException, java.io.IOException {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Producto no encontrado."));
        Map<String, Object> updates = createProductMap(id, name, product_description, price,stock, varietyId, subCategoryId, categoryId, productToUpdate);
        
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    productToUpdate.setName((String) value);
                    break;
                case "product_description":
                    productToUpdate.setProduct_description((String) value);
                    break;
                case "price":
                    productToUpdate.setPrice((Double) value);
                    break;
                case "stock":
                    productToUpdate.setStock((Integer) value);
                    break;
                case "varietyId":
                    productToUpdate.setVariety(varietyRepository.findById((Long) value).orElse(null));
                    break;
                case "subCategoryId":
                    productToUpdate.setSubCategory(subCategoryRepository.findById((Long) value).orElse(null));
                    break;
                case "categoryId":
                    productToUpdate.setCategory(categoryRepository.findById((Long) value).orElse(null));
                    break;
                default:
                    throw new IllegalArgumentException("Campo no reconocido: " + key);
            }
        });
        Product updatedProduct = productRepository.save(productToUpdate);

        if (updates.containsKey("price")) {
            updateProductCart(productToUpdate);//Si el precio cambio actualizo todos los carritos que tengan ese producto
        }
        return convertToDTO(updatedProduct);
    }
    

    @Override
    public ProductDTO createProduct(String name, String product_description, Double price, int stock,LocalDateTime date_published, boolean state,Long varietyId, Long subCategoryId,Long categoryId, List<MultipartFile> images) throws ProductNoSuchElementException, java.io.IOException, SerialException, SQLException, VarietyNoSuchElementException, CategoryNoSuchElementException, SubCategoryNoSuchElementException {
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
        product.setDiscount(0);

        Variety variety = varietyRepository.findById(varietyId)
                        .orElseThrow(() -> new VarietyNoSuchElementException());
        if (!variety.getState()) {
            throw new VarietyNoSuchElementException();
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNoSuchElementException());
        if (!category.getState()) {
            throw new CategoryNoSuchElementException();
        }

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNoSuchElementException());
        if (!subCategory.getState()) {
            throw new SubCategoryNoSuchElementException();
        }

        // Configurar las relaciones
        product.setVariety(varietyRepository.findById(varietyId).get());
        product.setSubCategory(subCategoryRepository.findById(subCategoryId).get());
        product.setCategory(categoryRepository.findById(categoryId).get());

        Product savedProduct = productRepository.save(product);

        addImages(savedProduct, images);
        
        return convertToDTO(savedProduct);
    }

    public ProductDTO convertToDTO(Product product) {

        List<ProductImage> images = productImageRepository.findAllByProductId(product.getProduct_id());
        
        List<ProductImageDTO> imageDTOs = images.stream().map(image -> {
            try {
                String encodedString = Base64.getEncoder()
                    .encodeToString(image.getImageData().getBytes(1, (int) image.getImageData().length()));

                return new ProductImageDTO(image.getId() ,encodedString);
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
        productDTO.setSubCategoryId(product.getSubCategory() != null ? product.getSubCategory().getSubCategory_id() : null);
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getCategory_id() : null);
        productDTO.setImagesList(imageDTOs);
        productDTO.setDiscount(product.getDiscount());
        return productDTO;
    }

    public void updateProductDiscount(Long productId, float discount){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        product.setDiscount(discount);
        productRepository.save(product);
        updateProductCart(product);
    }


    private void updateProductCart(Product product){
        //Product product = productRepository.findById(productId)
        //                    .orElseThrow(()-> new IllegalStateException("Producto no encontrado"));
        List<ProductsCart> productsCarts= product.getProductsCarts();
        productsCarts.forEach(productsCart -> productCartService.updateProductCart(productsCart, product));

    }

    public void deleteProductByCategory(Long categoyId){
        List<Product> products = productRepository.findByCategory(categoyId);
        products.forEach(product -> {
            product.setState(false);
            productRepository.save(product);
        });
    }

    public void deleteProductBySubCategory(Long subCategoryId){
        List<Product> products = productRepository.findBySubCategory(subCategoryId);
        products.forEach(product -> {
            product.setState(false);
            productRepository.save(product);
        });
    }
    
    public void deleteProductByVariety(Long varietyId){
        List<Product> products = productRepository.findByVariety(varietyId);
        products.forEach(product -> {
            product.setState(false);
            productRepository.save(product);
        });
    }

}
