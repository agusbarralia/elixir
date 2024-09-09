package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.SubCategory;
import com.elixir.elixir.exceptions.SubCategoryDuplicateException;
import com.elixir.elixir.exceptions.SubCategoryNoSuchElementException;
import com.elixir.elixir.repository.SubCategoryRepository;
import com.elixir.elixir.service.Interface.ProductService;
import com.elixir.elixir.service.Interface.SubCategoryService;

@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    @Autowired
    private SubCategoryRepository SubCategoryRepository;
    @Autowired
    private ProductService productService;

    public List<SubCategory> getSubCategories(){
        return SubCategoryRepository.findAllWithStateTrue();
    }

    public Optional<SubCategory> getSubCategoryByName(String subcategory_name) throws SubCategoryNoSuchElementException {
        Optional<SubCategory> subcategory = SubCategoryRepository.findBySubCategoryName(subcategory_name);
        if (subcategory.isPresent()) {
            return subcategory;
        } else {
            throw new SubCategoryNoSuchElementException();
        }
    }

    public SubCategory createSubCategory(String subcategory_name) throws SubCategoryDuplicateException{
        Optional<SubCategory> subcategories = SubCategoryRepository.findBySubCategoryName(subcategory_name);
        if(subcategories.isEmpty())
            return SubCategoryRepository.save(new SubCategory(subcategory_name));
        throw new SubCategoryDuplicateException();
        }

    public SubCategory deleteSubCategory(Long subCategoryId) throws SubCategoryNoSuchElementException{
        Optional<SubCategory> SubCategory = SubCategoryRepository.findById(subCategoryId);
        if (SubCategory.isPresent()) {
            SubCategory.get().setState(false);
            SubCategoryRepository.save(SubCategory.get());
            productService.deleteProductBySubCategory(subCategoryId);
            return SubCategory.get();
        } else {
            throw new SubCategoryNoSuchElementException();
        }
    }


}