package com.adverpix.ecommerce.dto;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private String userId;
    private Integer productId;
    private String productName;
    private int quantity;
    private double price;
    private byte[] image; // Base64 encoded string will be sent
}
