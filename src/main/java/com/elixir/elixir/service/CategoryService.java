package com.elixir.elixir.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;

import java.util.Optional; 

import com.elixir.elixir.entity.Category;

public interface CategoryService {
    
    public Page<Category> getCategories (PageRequest pageRequest);

    public Optional<Category> getCategoryByCategory_name(String category_name) throws CategoryNoSuchElementException;
    
    public Category createCategory(String category_name) throws CategoryDuplicateException;


}
