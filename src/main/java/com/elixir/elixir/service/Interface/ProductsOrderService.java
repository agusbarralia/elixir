package com.elixir.elixir.service.Interface;

import java.util.List;

import com.elixir.elixir.entity.dto.ProductsOrderDTO;
import com.elixir.elixir.entity.ProductsOrder;

public interface ProductsOrderService {
    
    public List<ProductsOrderDTO> convertAllToDTO(List<ProductsOrder> productsOrders);
    
    public ProductsOrderDTO convertToDTO(ProductsOrder productsOrder);

}
