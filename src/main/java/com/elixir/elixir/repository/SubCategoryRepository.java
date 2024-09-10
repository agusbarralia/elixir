package com.elixir.elixir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.elixir.elixir.entity.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    
    Optional<SubCategory> findByNameAndStateTrue(String name);

    @Query(value = "SELECT c FROM SubCategory c WHERE c.state = true")
    List<SubCategory> findAllWithStateTrue();

}
    