package com.elixir.elixir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
    
}
