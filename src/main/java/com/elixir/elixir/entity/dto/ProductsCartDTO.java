package com.elixir.elixir.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsCartDTO {
    
    private Long productscart_id;
    private int quantity;
    private Double unit_price;
    private Double discount_price;
    private Double subtotal;
    private Long cart_id;
    private Long product_id;
    
}
