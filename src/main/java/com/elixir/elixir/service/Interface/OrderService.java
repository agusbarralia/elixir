package com.elixir.elixir.service.Interface;

import java.util.List;

import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.exceptions.OrderNoSuchElementException;

public interface OrderService {

    public List<OrderDTO> getOrders();

    public List<OrderDTO> getOrdersByUserId();

    public OrderDTO getOrderById(Long order_id) throws OrderNoSuchElementException;

    public OrderDTO convertToOrderDTO(Order order);

}
