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

    // Variant fields for androidMobile and iosMobile
    private String processor;
    private String ram;
    private String storage;

    // Variant fields for clothes
    private String fabric;
    private String fit;
    private String pattern;

    // Variant fields for shoes
    private String material;
    private String closureType;
    private String soleMaterial;

    // ✅ Custom constructor matching the JPQL query
    public ProductSummaryDTO(int id, String name, String description, double price, int stock,
                             Float ratingCount, String imageUrls, int categoryId, int sellerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.ratingCount = ratingCount;
        this.imageUrls = imageUrls;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
    }
}