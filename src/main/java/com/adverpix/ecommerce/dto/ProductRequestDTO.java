package com.adverpix.ecommerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    private int ratingCount;
    private int categoryId;
    private int sellerId;
    private List<MultipartFile> images;
}
