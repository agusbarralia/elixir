package com.elixir.elixir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    
    @Query(value = "SELECT c FROM SubCategory c WHERE c.name = ?1")
    Optional<SubCategory> findBySubCategoryName(String name);
}
    