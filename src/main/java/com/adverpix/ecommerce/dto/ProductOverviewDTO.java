package com.adverpix.ecommerce.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverviewDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String imageUrl;
    private String sellerName;
    private String categoryName;
}
