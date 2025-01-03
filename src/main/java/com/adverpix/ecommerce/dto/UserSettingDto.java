package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String role;
    private String country;
    private String image_url;
    private MultipartFile image; // For uploading images
}
