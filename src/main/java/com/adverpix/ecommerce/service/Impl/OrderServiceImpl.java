package com.adverpix.ecommerce.service.Impl;

import com.adverpix.ecommerce.dto.OrderItemDto;
import com.adverpix.ecommerce.dto.OrderRequestDto;
import com.adverpix.ecommerce.dto.OrderResponseDto;
import com.adverpix.ecommerce.entity.*;
import com.adverpix.ecommerce.repository.CartItemRepository;
import com.adverpix.ecommerce.repository.OrderRepository;
import com.adverpix.ecommerce.repository.UserRepository;
import com.adverpix.ecommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<CartItem> selectedCartItems = cartRepository.findAllById(orderRequest.getCartItemIds());

        if (selectedCartItems.isEmpty()) {
            throw new IllegalArgumentException("No cart items found for given IDs");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = selectedCartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId((long) cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(BigDecimal.valueOf(cartItem.getPrice()));
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        // Delete the selected cart items
        cartRepository.deleteAll(selectedCartItems);

        return mapToOrderResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return mapToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Fetch all orders for this user
        List<Order> orders = orderRepository.findByUser(user);

        // Convert each Order to OrderResponseDto and return as a list
        return orders.stream()
                .map(this::mapToOrderResponseDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto mapToOrderResponseDto(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUser().getId());
        response.setOrderItems(order.getOrderItems().stream().map(item -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(item.getProductId());
            orderItemDto.setQuantity(item.getQuantity());
            return orderItemDto;
        }).collect(Collectors.toList()));
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}