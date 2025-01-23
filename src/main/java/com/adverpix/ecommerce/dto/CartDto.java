package com.adverpix.ecommerce.dto;

import lombok.Data;

@Data
public class CartDto {
    private Long id; // Primary key
    private String userId; // User Identifier
    private Integer productId; // Product Identifier
    private String productName; // Name of the Product
    private int quantity; // Quantity of Product
    private String imageUrl; // Image URL as String
    private double price; // Price of the Product
}
