package com.elixir.elixir.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.Role;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.entity.dto.ProductsOrderDTO;
import com.elixir.elixir.exceptions.OrderNoSuchElementException;
import com.elixir.elixir.repository.OrderRepository;
import com.elixir.elixir.service.Interface.OrderService;
import com.elixir.elixir.service.Interface.UserService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;

    //CORREGIR
    @Override
    public List<OrderDTO> getOrders() {
        return orderRepository.findAllByUser().stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    //CORREGIR
    @Override
    public List<OrderDTO> getOrdersByUserId() {
        Long user_id = userService.getCurrentUserId();
        return orderRepository.findByUser_id(user_id).stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long order_id) throws OrderNoSuchElementException {
    Long userId = userService.getCurrentUserId();
    User user = userService.getUserById(userId);
    
    Optional<Order> order = orderRepository.findById(order_id);

    if (order.isPresent()) {
        Order foundOrder = order.get();
        if (!foundOrder.getUser().getUser_id().equals(userId) && !user.getRole().equals(Role.ADMIN)) {
            throw new OrderNoSuchElementException();
        }
        return convertToOrderDTO(foundOrder);
    } else {
        throw new OrderNoSuchElementException();
    }
    }

    @Override
    public OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrder_id());
        orderDTO.setOrder_date(order.getOrder_date());
        orderDTO.setUserId(order.getUser().getUser_id());
        orderDTO.setProductsOrders(order.getProductOrders().stream()
                .map(productsOrder -> {
                    ProductsOrderDTO productsOrderDTO = new ProductsOrderDTO();
                    productsOrderDTO.setProductOrderId(productsOrder.getProductorder_id());
                    productsOrderDTO.setQuantity(productsOrder.getQuantity());
                    productsOrderDTO.setUnit_price(productsOrder.getUnit_price());
                    productsOrderDTO.setSubtotal(productsOrder.getSubtotal());
                    productsOrderDTO.setOrderId(productsOrder.getOrder().getOrder_id());
                    productsOrderDTO.setProductDTO(productService.convertToDTO(productsOrder.getProduct()));
                    return productsOrderDTO;
                }).collect(Collectors.toList()));
        orderDTO.setTotal(order.getTotal());
        return orderDTO;
    }

}
