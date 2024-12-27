package com.adverpix.ecommerce.service.Impl;

import com.adverpix.ecommerce.dto.OrderItemDto;
import com.adverpix.ecommerce.dto.OrderRequestDto;
import com.adverpix.ecommerce.dto.OrderResponseDto;
import com.adverpix.ecommerce.entity.Order;
import com.adverpix.ecommerce.entity.OrderItem;
import com.adverpix.ecommerce.entity.OrderStatus;
import com.adverpix.ecommerce.entity.User;
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
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(BigDecimal.valueOf(100)); // Replace with actual product price lookup
            orderItem.setOrder(order); // Set the order reference
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

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