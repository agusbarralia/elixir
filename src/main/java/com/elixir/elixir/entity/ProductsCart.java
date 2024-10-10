package com.elixir.elixir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductsCart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productscart_id;

    @Column
    private int quantity;

    @Column
    private Double unit_price;

    @Column
    private Double discount_price;

    @Column
    private Double subtotal;

    @Column
    private float discount;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
