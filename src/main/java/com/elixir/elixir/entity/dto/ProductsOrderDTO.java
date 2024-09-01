package com.elixir.elixir.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOrderDTO {
    
    private Long productOrderId;
    private int quantity;
    private double unit_price;
    private double subtotal;
    private Long orderId;
    private ProductDTO productDTO;

}
