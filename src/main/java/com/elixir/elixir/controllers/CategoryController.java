package com.elixir.elixir.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.elixir.elixir.entity.Category;
import com.elixir.elixir.exceptions.CategoryDuplicateException;
import com.elixir.elixir.service.CategoryService;

import java.net.URI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<Page<Category>> getCategories (
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(categoryService.getCategories(PageRequest.of(0,Integer.MAX_VALUE)));
        return ResponseEntity.ok(categoryService.getCategories(PageRequest.of(page, size)));
    }

    @PostMapping("/{category_name}")
    public ResponseEntity<Object> createCategory(@PathVariable String category_name)
        throws CategoryDuplicateException{
            Category result = categoryService.createCategory(category_name);
            return ResponseEntity.created(URI.create("/categories/" + result.getCategory_id())).body(result);
        }
    }   
    
    
    
