package com.elixir.elixir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.elixir.elixir.entity.OrderState;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    
    Optional<OrderState> findByName(String name);

}
