package com.elixir.elixir.repository;

import com.elixir.elixir.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    
    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p WHERE p.category.category_id = ?1")
    List<Product> findByCategory(Long category_id);

    @Query("SELECT p FROM Product p WHERE p.subCategory.subCategory_id = ?1")
    List<Product> findBySubCategory(Long subCategory_id);

    @Query("SELECT p FROM Product p WHERE p.variety.variety_id = ?1")
    List<Product> findByVariety(Long variety_id);

}
