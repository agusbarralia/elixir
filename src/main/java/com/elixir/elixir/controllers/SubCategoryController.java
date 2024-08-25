package com.elixir.elixir.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.SubCategoryDuplicateException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;
import com.elixir.elixir.service.Interface.SubCategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("subcategories")

public class SubCategoryController {
    @Autowired
    private SubCategoryService subcategoryService;

    @GetMapping
    public List<SubCategory> getSubCategories() {
        return subcategoryService.getSubCategories();
    }

    @GetMapping("/{subcategory_name}")
    public ResponseEntity<SubCategory> getSubCategoryByName(@PathVariable String subcategory_name)
        throws SubCategoryNoSuchElementException{
            return ResponseEntity.ok(subcategoryService.getSubCategoryByName(subcategory_name).get());
    }
    
    @PostMapping("/{subcategory_name}")
    public ResponseEntity<SubCategory> createSubCategory(@PathVariable String subcategory_name)
        throws SubCategoryDuplicateException{
            SubCategory result = subcategoryService.createSubCategory(subcategory_name);
            return ResponseEntity.created(URI.create("/subcategories/" + result.getSubcategory_id())).body(result);
        }
    }   
    
    

