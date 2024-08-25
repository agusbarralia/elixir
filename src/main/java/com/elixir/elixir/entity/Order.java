package com.elixir.elixir.entity;

import java.util.List;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long order_id;
  
  @Column
  private LocalDateTime order_date;
  
  @Column
  private double total;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "orderstate_id", referencedColumnName = "orderstate_id")
  private OrderState orderState;

  @OneToMany(mappedBy = "order")
  private List<ProductsOrder> productOrders;
}
