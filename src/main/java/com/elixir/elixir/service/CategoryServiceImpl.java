package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;
//import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.elixir.elixir.exceptions.CategoryDuplicateException;


import com.elixir.elixir.entity.Category;
//import com.elixir.elixir.entity.Product;
import com.elixir.elixir.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable){
        return categoryRepository.findAll(pageable);
    }

    public Category createCategory(String category_name) throws CategoryDuplicateException{
        Optional<Category> categories = categoryRepository.findByCategory_name(category_name);
        if(categories.isEmpty())
            return categoryRepository.save(new Category(category_name));
        throw new CategoryDuplicateException();
        }
}