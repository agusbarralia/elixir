package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elixir.elixir.entity.Category;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.exceptions.CategoryNoSuchElementException;
import com.elixir.elixir.service.Interface.CategoryService;

import java.net.URI;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{category_name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String category_name)
        throws CategoryNoSuchElementException{
            return ResponseEntity.ok(categoryService.getCategoryByName(category_name).get());
    }
    
    @PostMapping("/{category_name}")
    public ResponseEntity<Category> createCategory(@PathVariable String category_name)
        throws CategoryDuplicateException{
            Category result = categoryService.createCategory(category_name);
            return ResponseEntity.created(URI.create("/categories/" + result.getCategory_id())).body(result);
        }
    }   
    
    
    
