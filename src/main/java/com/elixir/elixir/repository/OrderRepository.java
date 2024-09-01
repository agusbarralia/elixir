package com.elixir.elixir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findById(Long order_id);

}
