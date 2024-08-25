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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderstate_id;

    @Column
    private String name;

    @OneToMany(mappedBy = "orderState")
    private List<Order> orders;
}
