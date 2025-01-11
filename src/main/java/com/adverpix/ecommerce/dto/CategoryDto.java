package com.adverpix.ecommerce.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private int categoryId;
    private String name;
    private String description;
    private Integer sellerId;
}
