package com.adverpix.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long userId;
    private List<OrderItemDto> orderItems;
}