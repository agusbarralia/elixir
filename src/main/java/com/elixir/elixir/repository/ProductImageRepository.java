package com.elixir.elixir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

import com.elixir.elixir.entity.ProductImage;
import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImage p WHERE p.product.product_id = ?1")
    void deleteByProductId(Long productId);
    
    @Query("SELECT p FROM ProductImage p WHERE p.product.product_id = ?1")
    List<ProductImage> findAllByProductId(Long productId);
}
