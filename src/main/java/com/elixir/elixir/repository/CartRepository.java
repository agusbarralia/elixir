package com.elixir.elixir.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.User;



@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.user_id = ?1")
    Optional<Cart> findByUserId(Long user_id);

    @Query("SELECT u FROM User u WHERE u.user_id = ?1")
    Optional<User> findUserById(Long user_id);

}
