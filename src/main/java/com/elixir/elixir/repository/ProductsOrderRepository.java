package com.elixir.elixir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.ProductsOrder;

@Repository
public interface ProductsOrderRepository extends JpaRepository<ProductsOrder, Long> {
    
}
