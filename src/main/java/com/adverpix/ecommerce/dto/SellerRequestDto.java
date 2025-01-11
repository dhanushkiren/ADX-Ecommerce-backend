package com.adverpix.ecommerce.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRequestDto {
    private int id;
    private String name;
    private String email;
    private String address;
    private String contact_number;
    private String description;
    private String password ;
}