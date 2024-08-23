package com.elixir.elixir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    @Query(value = "SELECT c FROM Category c WHERE c.name = ?1")
    Optional<Category> findByCategoryName(String name);

}
