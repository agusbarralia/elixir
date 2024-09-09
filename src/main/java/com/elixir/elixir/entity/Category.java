package com.elixir.elixir.entity;

import java.util.List;

import jakarta.persistence.CascadeType;

//import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    public Category(String name) {
    this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column
    private String name;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean state = true;

}