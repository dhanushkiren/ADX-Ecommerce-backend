package com.adverpix.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Float ratingCount;
    private String review;
    private int price;
    private String offerDetails;
    private String brand;
    private String imageUrl;

}
