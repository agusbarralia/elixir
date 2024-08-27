package com.elixir.elixir.repository;

import com.elixir.elixir.entity.ProductsCart;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductsCart, Long> {

    @Query(value = "SELECT c FROM ProductsCart c WHERE c.cart.cart_id = ?1")
    List<ProductsCart> findByCartId(Long cart_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ProductsCart c WHERE c.cart.cart_id = ?1")
    void deleteByCartId(Long cart_id);

}
