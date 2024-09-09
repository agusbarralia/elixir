package com.elixir.elixir.entity;

import jakarta.persistence.Column;

//import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Variety {

    public Variety(String name) {
        this.name = name;
        }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variety_id;

    private String name;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean state = true;

    //@OneToMany(mappedBy = "label")
    //private List<Product> products;
}