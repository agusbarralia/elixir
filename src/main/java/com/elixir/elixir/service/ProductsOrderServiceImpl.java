package com.elixir.elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.elixir.elixir.entity.ProductsOrder;
import com.elixir.elixir.entity.dto.ProductsOrderDTO;
import com.elixir.elixir.service.Interface.ProductsOrderService;

import io.jsonwebtoken.lang.Collections;

@Service
public class ProductsOrderServiceImpl implements ProductsOrderService {

    @Autowired
    private ProductServiceImpl productService;

    @Override
    public List<ProductsOrderDTO> convertAllToDTO(List<ProductsOrder> productsOrders) {
        if (productsOrders != null) {
            return productsOrders.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());    
        }
        return Collections.emptyList();
    }
    
    public ProductsOrderDTO convertToDTO(ProductsOrder productsOrder) {
        ProductsOrderDTO productsOrderDTO = new ProductsOrderDTO();
        productsOrderDTO.setProductOrderId(productsOrder.getProductorder_id());
        productsOrderDTO.setQuantity(productsOrder.getQuantity());
        productsOrderDTO.setUnit_price(productsOrder.getUnit_price());
        productsOrderDTO.setSubtotal(productsOrder.getSubtotal());
        productsOrderDTO.setOrderId(productsOrder.getOrder().getOrder_id());
        productsOrderDTO.setProductDTO(productService.convertToDTO(productsOrder.getProduct()));
        return productsOrderDTO;
    }
}
