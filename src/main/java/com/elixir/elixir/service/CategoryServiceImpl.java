package com.elixir.elixir.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.entity.Category;
import com.elixir.elixir.repository.CategoryRepository;
import com.elixir.elixir.service.Interface.CategoryService;
import com.elixir.elixir.service.Interface.ProductService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAllWithStateTrue();
    }

    public Optional<Category> getCategoryByName(String category_name) throws CategoryNoSuchElementException {
        Optional<Category> category = categoryRepository.findByNameAndStateTrue(category_name);
        if (category.isPresent() && category.get().getState()) {
            return category;
        } else {
            throw new CategoryNoSuchElementException();
        }
    }

    public Category createCategory(String category_name) throws CategoryDuplicateException {
        Optional<Category> existingCategory = categoryRepository.findByNameAndStateTrue(category_name);
    
        if (existingCategory.isPresent()) {
            if (existingCategory.get().getState()) {
                throw new CategoryDuplicateException();
            } else {
                // Si la categoría existe pero su estado es false, se puede crear una nueva categoría
                return categoryRepository.save(new Category(category_name));
            }
        } else {
            return categoryRepository.save(new Category(category_name));
        }
    }

    public Category deleteCategory(String category_name) throws CategoryNoSuchElementException{
        Category category = categoryRepository.findByNameAndStateTrue(category_name)
                        .orElseThrow(() -> new IllegalStateException("Categoria no encontrada"));

        category.setState(false);
        categoryRepository.save(category);
        productService.deleteProductByCategory(category.getCategory_id());
        return category;
        
    }
}