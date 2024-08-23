package com.elixir.elixir.service;

import java.util.List;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;

import java.util.Optional; 

import com.elixir.elixir.entity.Category;

public interface CategoryService {
    
    public List<Category> getCategories();

    public Optional<Category> getCategoryByName(String category_name) throws CategoryNoSuchElementException;
    
    public Category createCategory(String category_name) throws CategoryDuplicateException;


}
