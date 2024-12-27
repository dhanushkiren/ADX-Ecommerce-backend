package com.adverpix.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
    private Long productId;
    private int quantity;
}
