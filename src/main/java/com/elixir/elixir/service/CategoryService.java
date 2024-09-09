package com.elixir.elixir.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;

public interface CategoryService {
    
    public Page<Category> getCategories (PageRequest pageRequest);

    public Category deleteCategory(Long categoryId) throws CategoryNoSuchElementException;
    
}
