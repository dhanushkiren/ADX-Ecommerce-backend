package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.UserSettingDto;
import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    @PostMapping
    public ResponseEntity<UserSettingDto> createUser(@RequestParam(defaultValue = "image", required = false , name = "image") MultipartFile image,
                                                     @RequestParam("username") String username,
                                                     @RequestParam("firstName") String firstName,
                                                     @RequestParam(defaultValue = "", required = false, name = "lastName") String lastName,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("addresses") String addresses,
                                                     @RequestParam("mobile") String mobile,
                                                     @RequestParam("role") String role,
                                                     @RequestParam("date_of_birth") Date date_of_birth,
                                                     @RequestParam("country") String country) throws IOException {
        UserSettingDto userSettingDto = new UserSettingDto();// Creating a UserSettingDto with the received data
        userSettingDto.setUsername(username);
        userSettingDto.setFirstName(firstName);
        if(lastName != null) userSettingDto.setLastName(lastName);
        userSettingDto.setEmail(email);
        userSettingDto.setAddresses(List.of(addresses));
        userSettingDto.setMobile(mobile);
        userSettingDto.setRole(role);
        userSettingDto.setDate_of_birth(date_of_birth);
        userSettingDto.setCountry(country);
        if (image != null) {
            try {
                userSettingDto.setImage(java.util.Base64.getEncoder().encodeToString(image.getInputStream().readAllBytes()));
            } catch (IOException e) {
                // handle the exception
            }
        }
        User user = userSettingsService.createUser(userSettingDto);// Creating the User entity
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));// Returning the created user as a DTO
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
    public ResponseEntity<UserSettingDto> updateUser(@PathVariable("id") Long id,
                                                     @RequestParam(defaultValue = "image", required = false , name = "image") MultipartFile image,
                                                     @RequestParam("firstName") String firstName,
                                                     @RequestParam(defaultValue = "", required = false, name = "lastName") String lastName,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("addresses") String addresses,
                                                     @RequestParam("mobile") String mobile,
                                                     @RequestParam("date_of_birth")@DateTimeFormat(pattern = "dd/MM/yyyy") Date date_of_birth,
                                                     @RequestParam("country") String country) throws IOException {
        UserSettingDto userSettingDto = new UserSettingDto(); // Creating a UserSettingDto with the received data
        userSettingDto.setFirstName(firstName);
        if(lastName != null) userSettingDto.setLastName(lastName);
        userSettingDto.setEmail(email);
        userSettingDto.setAddresses(List.of(addresses));
        userSettingDto.setMobile(mobile);
        userSettingDto.setDate_of_birth(date_of_birth);
        userSettingDto.setCountry(country);
        if (image != null) {
            try {
                userSettingDto.setImage(java.util.Base64.getEncoder().encodeToString(image.getInputStream().readAllBytes()));
            } catch (IOException e) {
                // handle the exception
            }
        }
        User user = userSettingsService.updateUser(id, userSettingDto);// Updating the User entity
        return ResponseEntity.ok(userSettingsService.mapEntityToDTO(user));// Returning the updated user as a DTO
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

}
