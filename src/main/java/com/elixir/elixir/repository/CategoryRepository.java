package com.elixir.elixir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;



import com.elixir.elixir.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);

    @Query ("SELECT c FROM Category c WHERE c.state = true")
    List<Category> findAllWithStateTrue();

}
