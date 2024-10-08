package com.elixir.elixir.service.Interface;

import java.util.List;
import java.util.Optional;

import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.SubCategoryDuplicateException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;


public interface SubCategoryService {
    
    public List<SubCategory> getSubCategories();

    public Optional<SubCategory> getSubCategoryByName(String subcategory_name) throws SubCategoryNoSuchElementException;

    public SubCategory createSubCategory(String subcategory_name) throws SubCategoryDuplicateException;

    public SubCategory deleteSubCategory(String subCategory_name) throws SubCategoryNoSuchElementException;

}