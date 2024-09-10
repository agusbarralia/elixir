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
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private ProductService productService;

    public List<SubCategory> getSubCategories(){
        return subCategoryRepository.findAllWithStateTrue();
    }

    public Optional<SubCategory> getSubCategoryByName(String subcategory_name) throws SubCategoryNoSuchElementException {
        Optional<SubCategory> subcategory = subCategoryRepository.findByNameAndStateTrue(subcategory_name);
        if (subcategory.isPresent()) {
            return subcategory;
        } else {
            throw new SubCategoryNoSuchElementException();
        }
    }

    public SubCategory createSubCategory(String subcategory_name) throws SubCategoryDuplicateException {
        Optional<SubCategory> existingSubCategory = subCategoryRepository.findByNameAndStateTrue(subcategory_name);
    
        if (existingSubCategory.isPresent()) {
            if (existingSubCategory.get().getState()) {
                throw new SubCategoryDuplicateException();
            } else {
                // Si la subcategoría existe pero su estado es false, se puede crear una nueva subcategoría
                return subCategoryRepository.save(new SubCategory(subcategory_name));
            }
        } else {
            return subCategoryRepository.save(new SubCategory(subcategory_name));
        }
    }

    public SubCategory deleteSubCategory(Long subCategoryId) throws SubCategoryNoSuchElementException{
        Optional<SubCategory> SubCategory = subCategoryRepository.findById(subCategoryId);
        if (SubCategory.isPresent()) {
            SubCategory.get().setState(false);
            subCategoryRepository.save(SubCategory.get());
            productService.deleteProductBySubCategory(subCategoryId);
            return SubCategory.get();
        } else {
            throw new SubCategoryNoSuchElementException();
        }
    }


}