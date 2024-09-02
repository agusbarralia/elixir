package com.elixir.elixir.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column
    private String name;

    @Column
    private String product_description;

    @Column
    private Double price;

    @Column
    private int stock;

    @Column
    private LocalDateTime date_published;

    @Column
    private boolean state;

    @ManyToOne
    @JoinColumn(name = "label_id", referencedColumnName = "label_id")
    private Label label;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "subcategory_id")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;


    @OneToMany(mappedBy = "product")
    private List<ProductsCart> productsCarts;

    @OneToMany(mappedBy = "product")
    private List<ProductsOrder> productOrders;

    @OneToMany(mappedBy ="product")
    private List<ProductImage> productImages;

    public boolean getState() {
        return state;
    }

    public void setState(boolean newState) {
        this.state = newState;
    }
}