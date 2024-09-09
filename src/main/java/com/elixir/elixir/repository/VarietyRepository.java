package com.elixir.elixir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elixir.elixir.entity.Variety;

@Repository
public interface VarietyRepository extends JpaRepository<Variety, Long> {
    
    @Query(value = "SELECT c FROM Variety c WHERE c.name = ?1")
    Optional<Variety> findVarietyByName(String name);

    @Query(value = "SELECT c FROM Variety c WHERE c.state = true")
    List<Variety> findAllWithStateTrue();

}


