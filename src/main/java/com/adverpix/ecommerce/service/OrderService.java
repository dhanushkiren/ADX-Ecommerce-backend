package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.OrderRequestDto;
import com.adverpix.ecommerce.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
        OrderResponseDto createOrder(OrderRequestDto orderRequest);
        OrderResponseDto getOrderById(Long orderId);
        List<OrderResponseDto> getOrdersByUserId(Long userId);

}
