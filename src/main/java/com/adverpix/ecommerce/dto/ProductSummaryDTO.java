package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Float ratingCount;
    private String imageUrls;
    private int categoryId;
    private int sellerId;
}
