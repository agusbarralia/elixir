package com.elixir.elixir.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {

    public SubCategory(String name) {
        this.name = name;
        }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subcategory_id;

    @Column
    private String name;

    //@OneToMany(mappedBy = "subCategory")
    //private List<Product> products;
}
