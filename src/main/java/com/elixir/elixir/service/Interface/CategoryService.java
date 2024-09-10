package com.elixir.elixir.service.Interface;

import java.util.List;
import java.util.Optional;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;

public interface CategoryService {
    
    public List<Category> getCategories ();

    public Optional<Category> getCategoryByName(String category_name) throws CategoryNoSuchElementException;

    public Category createCategory(String category_name) throws CategoryDuplicateException;

    public Category deleteCategory(Long categoryId) throws CategoryNoSuchElementException;
    
}
