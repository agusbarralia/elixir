package com.elixir.elixir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    
}
