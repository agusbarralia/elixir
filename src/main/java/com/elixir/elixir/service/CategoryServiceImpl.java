package com.elixir.elixir.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.entity.Category;
import com.elixir.elixir.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable){
        return categoryRepository.findAll(pageable);
    }

    /* 
    public Optional<Category> getCategoryByCategory_name(String category_name){
        Optional<Category> categories = categoryRepository.findByCategory_name(category_name);
        if (categories.isEmpty())
            return Optional.empty();
        return categoryRepository.findByCategory_name(category_name);
    }
    */
    public Optional<Category> getCategoryByCategory_name(String category_name) throws CategoryNoSuchElementException {
        Optional<Category> category = categoryRepository.findByCategory_name(category_name);
        if (category.isPresent()) {
            return category;
        } else {
            throw new CategoryNoSuchElementException();
        }
    }

    public Category createCategory(String category_name) throws CategoryDuplicateException{
        Optional<Category> categories = categoryRepository.findByCategory_name(category_name);
        if(categories.isEmpty())
            return categoryRepository.save(new Category(category_name));
        throw new CategoryDuplicateException();
        }
}