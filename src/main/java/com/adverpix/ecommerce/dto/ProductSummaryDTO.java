package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDTO {
    private String name;
    private int price;
    private int ratingCount;
    private String imageUrl;
    private int stock;
    private int numberOfReviews;
}
