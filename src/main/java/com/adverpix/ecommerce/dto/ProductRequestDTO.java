package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    private Float ratingCount;
    private int categoryId;
    private int sellerId;
    private List<MultipartFile> images;

    // Mobile-specific fields
    private String processor;
    private String ram;
    private String storage;

    // Clothes-specific fields
    private String fabric;
    private String fit;
    private String pattern;

    // Shoes-specific fields
    private String material;
    private String closureType;
    private String soleMaterial;
}