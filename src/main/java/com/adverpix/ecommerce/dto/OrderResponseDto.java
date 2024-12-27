package com.adverpix.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
