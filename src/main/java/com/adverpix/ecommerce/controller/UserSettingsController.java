package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.UserSettingDto;
import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.or;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@RestController
@RequestMapping("/api/users")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;


    @PostMapping
    public ResponseEntity<UserSettingDto> createUser(
            @RequestParam(defaultValue = "image", required = false , name = "image") MultipartFile image,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(name ="firstName", required = false) String firstName,
            @RequestParam(defaultValue = "", required = false, name = "lastName") String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "addresses", required = false) List<String> addresses,
            @RequestParam(value = "mobile",required = false) String mobile,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "date_of_birth", required = false) String date_of_birth,
            @RequestParam(value = "country", required = false) String country
    ) throws IOException {
        UserSettingDto userSettingDto = new UserSettingDto();
        userSettingDto.setUsername(username);
        userSettingDto.setFirstName(firstName);
        if(lastName != null) userSettingDto.setLastName(lastName);
        userSettingDto.setEmail(email);
        userSettingDto.setAddresses(addresses);  // ✅ Now it's directly a list!
        userSettingDto.setMobile(mobile);
        userSettingDto.setRole(role);
        userSettingDto.setDate_of_birth(date_of_birth);
        userSettingDto.setCountry(country);
        userSettingDto.setImages(List.of(image));

        User user = userSettingsService.createUser(userSettingDto);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserSettingDto>> getAllUsers() { // Method to get all users
        List<UserSettingDto> users = userSettingsService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSettingDto> getUserById(@PathVariable("id") Long id) {
        UserSettingDto userSettingDto = userSettingsService.getUserById(id);
        return ResponseEntity.ok(userSettingDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSettingDto> updateUser(
            @PathVariable("id") Long id,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "addresses") List<String> addresses,
            @RequestParam(name = "mobile") String mobile,
            @RequestParam(name = "date_of_birth") String date_of_birth,
            @RequestParam(name = "country") String country
    ) throws IOException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserSettingDto userSettingDto = new UserSettingDto();

        if (firstName != null && !firstName.isEmpty()) {
            userSettingDto.setFirstName(firstName);
        }
        if(lastName != null && !lastName.isEmpty()) userSettingDto.setLastName(lastName);
        if(email != null && !email.isEmpty())userSettingDto.setEmail(email);
        if (password != null && !password.isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(password);
            userSettingDto.setPassword(encryptedPassword);
        }
        if (addresses != null && !addresses.isEmpty()) userSettingDto.setAddresses(addresses);
        if (mobile != null && !mobile.isEmpty())userSettingDto.setMobile(mobile);
        if (date_of_birth != null && !date_of_birth.isEmpty()) userSettingDto.setDate_of_birth(date_of_birth);
        if (country != null && !country.isEmpty())userSettingDto.setCountry(country);
        if (image != null && !image.isEmpty())userSettingDto.setImages(List.of(image));

        User user = userSettingsService.updateUser(id, userSettingDto);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userSettingsService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/addresses")
    public ResponseEntity<UserSettingDto> addAddress(@PathVariable("id") Long userId,
                                                     @RequestBody String newAddress) {
        User user = userSettingsService.addAddress(userId, newAddress);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }

    @DeleteMapping("/{id}/addresses")
    public ResponseEntity<UserSettingDto> removeAddress(@PathVariable("id") Long userId,
                                                        @RequestBody String addressToRemove) {
        User user = userSettingsService.removeAddress(userId, addressToRemove);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }
    ///

    @PutMapping("/{id}/email")
    public ResponseEntity<UserSettingDto> addEmail(@PathVariable("id") Long userId,
                                                   @RequestBody String newEmail) {
        User user = userSettingsService.updateEmail(userId, newEmail);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }
    @PutMapping("/{id}/number")
    public ResponseEntity<UserSettingDto> addNumber(@PathVariable("id") Long userId,
                                                    @RequestBody String newNumber) {
        User user = userSettingsService.updateNumber(userId, newNumber);
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));
    }
//    @PutMapping("/{id}/image")
//    public ResponseEntity<User> updateImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
//        try {
//            User user = userSettingsService.updateImage(id, image);
//            return ResponseEntity.ok(user);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}