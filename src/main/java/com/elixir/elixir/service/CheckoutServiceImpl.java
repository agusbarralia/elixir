package com.elixir.elixir.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.Cart;
import com.elixir.elixir.entity.Order;
import com.elixir.elixir.entity.ProductsOrder;
import com.elixir.elixir.entity.dto.OrderDTO;
import com.elixir.elixir.repository.CartRepository;
import com.elixir.elixir.repository.OrderRepository;
import com.elixir.elixir.repository.OrderStateRepository;
import com.elixir.elixir.repository.ProductCartRepository;
import com.elixir.elixir.repository.ProductsOrderRepository;
import com.elixir.elixir.service.Interface.CheckoutService;
import com.elixir.elixir.service.Interface.UserService;

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

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserService userService;

    public OrderDTO checkout() throws IllegalStateException {

        Long userId = userService.getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Carrito no encontrado para el usuario"));

        if (cart.getProducts().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }
        
        cart.getProducts().forEach(productsCart -> {
            int availableStock = productsCart.getProduct().getStock();
            int requestedQuantity = productsCart.getQuantity();

            if (requestedQuantity > availableStock) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + productsCart.getProduct().getName());
            }
        });

        Order order = new Order();
        order.setUser(userService.getUserById(userId));
        
        order.setOrderState(orderStateRepository.findByName("Pendiente").get()); // Asigna el estado "Pendiente" u otro estado inicial
        order.setOrder_date(LocalDateTime.now());

        List<ProductsOrder> productsOrderList = cart.getProducts().stream().map(productsCart -> {
            ProductsOrder productsOrder = new ProductsOrder();
            productsOrder.setOrder(order);
            productsOrder.setUnit_price(productsCart.getDiscount_price());
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
        cart.getProducts().forEach(productsCart -> {
            int remainingStock = productsCart.getProduct().getStock() - productsCart.getQuantity();
            productsCart.getProduct().setStock(remainingStock); // Actualiza el stock del producto
        });
        
        // Limpia el carrito después del checkout
        productCartRepository.deleteByCartId(cart.getCart_id());
        
        return orderService.convertToOrderDTO(order);
    }

    public Float getTotal(List<ProductsOrder> productsOrderList) {
        return (float) productsOrderList.stream().mapToDouble(ProductsOrder::getSubtotal).sum();
    }

}
