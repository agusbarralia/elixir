package com.elixir.elixir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT o FROM Order o ORDER BY o.user.id")
    List<Order> findAllByUser();

    Optional<Order> findById(Long order_id);

    @Query("SELECT o FROM Order o WHERE o.user.user_id = :user_id")
    List<Order> findByUser_id(Long user_id);

    @Query("SELECT o FROM Order o WHERE o.user.user_id = :user_id ORDER BY o.order_date DESC")
    Optional<Order> findLatestOrderByUserId(Long user_id);

}
