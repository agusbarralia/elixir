package com.elixir.elixir.entity.dto;

import java.util.List;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long orderId;
    private LocalDateTime order_date;
    private Long userId;
    private String state;
    private List<ProductsOrderDTO> productsOrders;
    private double total;

}
