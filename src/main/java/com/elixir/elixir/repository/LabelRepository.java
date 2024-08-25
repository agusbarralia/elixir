package com.elixir.elixir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    
    @Query(value = "SELECT c FROM Label c WHERE c.name = ?1")
    Optional<Label> findByLabelName(String name);
}
    

