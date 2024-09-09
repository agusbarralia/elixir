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
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryByName(String category_name) throws CategoryNoSuchElementException {
        Optional<Category> category = categoryRepository.findByName(category_name);
        if (category.isPresent()) {
            return category;
        } else {
            throw new CategoryNoSuchElementException();
        }
    }

    public Category createCategory(String category_name) throws CategoryDuplicateException{
        Optional<Category> categories = categoryRepository.findByName(category_name);
        if(categories.isEmpty())
            return categoryRepository.save(new Category(category_name));
        throw new CategoryDuplicateException();
        }

    public Category deleteCategory(Long categoryId) throws CategoryNoSuchElementException{
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            category.get().setState(false);
            categoryRepository.save(category.get());
            productService.deleteProductByCategory(categoryId);
            return category.get();
        } else {
            throw new CategoryNoSuchElementException();
        }
    }
}