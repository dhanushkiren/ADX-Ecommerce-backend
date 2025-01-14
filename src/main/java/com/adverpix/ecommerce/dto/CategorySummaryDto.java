package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryDto {
    private Integer category_id;
    private String randomImage;
    private Integer minPrice;
    private String categoryName;
}