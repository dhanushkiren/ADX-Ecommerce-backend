package com.adverpix.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> addresses;
    private String mobile;
    private String role;
    private String country;
    private String image_url;
    private MultipartFile image; // For uploading images
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_of_birth;
}
