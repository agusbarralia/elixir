package com.elixir.elixir.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.elixir.elixir.entity.Category;

public interface CategoryService {
    
    public Page<Category> getCategories (PageRequest pageRequest);
    
}
