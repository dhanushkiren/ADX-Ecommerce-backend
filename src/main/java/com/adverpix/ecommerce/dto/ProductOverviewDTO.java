package com.adverpix.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOverviewDTO {
    private int id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private String imageUrl;
    private String sellerName;
    private String categoryName;

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

    // ✅ Explicit constructor to support JPQL `new` keyword usage
    public ProductOverviewDTO(
            int id, String name, String description, int price, int stock, String imageUrl,
            String sellerName, String categoryName,
            String processor, String ram, String storage,
            String fabric, String fit, String pattern,
            String material, String closureType, String soleMaterial
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.sellerName = sellerName;
        this.categoryName = categoryName;
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
        this.fabric = fabric;
        this.fit = fit;
        this.pattern = pattern;
        this.material = material;
        this.closureType = closureType;
        this.soleMaterial = soleMaterial;
    }
}