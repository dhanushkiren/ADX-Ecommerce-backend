package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private String name;
    private String description;
    private int price;
    private double rating;
    private String review;
    private String offerDetails;
    private int quantity;
    private String imageUrl;
    private String sellerName;
    private int stock;
    private int numberOfReviews;
    private String brand;
}