package com.elixir.elixir.repository;

import com.elixir.elixir.entity.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    
    Optional<Product> findByName(String name);
}
