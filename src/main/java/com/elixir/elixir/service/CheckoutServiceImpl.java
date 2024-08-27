package com.elixir.elixir.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.OrderState;
import com.elixir.elixir.entity.ProductsOrder;
import com.elixir.elixir.entity.User;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.repository.OrderRepository;
import com.elixir.elixir.repository.OrderStateRepository;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.repository.ProductsOrderRepository;
import com.elixir.elixir.service.Interface.CheckoutService;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductsOrderRepository productsOrderRepository;

    @Autowired
    private ProductCartRepository productCartRepository;

    public Order checkout(User user) {
        //VALIDAR QUE EL CARRITO NO ESTÉ VACÍO
        //PREGUNTAR SI HAY STOCK
        Cart cart = cartRepository.findByUserId(user.getUser_id()).get();
        Order order = new Order();
        order.setUser(user);
        order.setOrderState(orderStateRepository.findByName("Pending").get()); // Asigna el estado "Pendiente" u otro estado inicial
        order.setOrder_date(LocalDateTime.now());

        List<ProductsOrder> productsOrderList = cart.getProducts().stream().map(productsCart -> {
            ProductsOrder productsOrder = new ProductsOrder();
            productsOrder.setOrder(order);
            productsOrder.setProduct(productsCart.getProduct());
            productsOrder.setQuantity(productsCart.getQuantity());
            productsOrder.setSubtotal(productsCart.getSubtotal());
            return productsOrder;
        }).collect(Collectors.toList());

        order.setTotal(getTotal(productsOrderList));
        order.setProductOrders(productsOrderList);

        orderRepository.save(order);
        productsOrderRepository.saveAll(productsOrderList);

        // Descontar stock
        // Limpia el carrito después del checkout
        productCartRepository.deleteByCartId(cart.getCart_id());

        return order;
    }

    public Float getTotal(List<ProductsOrder> productsOrderList) {
        return (float) productsOrderList.stream().mapToDouble(ProductsOrder::getSubtotal).sum();
    }

}
