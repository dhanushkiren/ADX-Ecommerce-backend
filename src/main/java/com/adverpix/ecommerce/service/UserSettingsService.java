package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.UserSettingDto;
import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserSettingsService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserSettingDto userSettingDto) throws IOException {
        User user = new User();
        user.setUsername(userSettingDto.getUsername());
        user.setFirstName(userSettingDto.getFirstName());
        if (userSettingDto.getLastName() == null) {
            user.setLastName(null);
        }
        else {
            user.setLastName(userSettingDto.getLastName());
        }
        user.setEmail(userSettingDto.getEmail());
        user.setAddresses(userSettingDto.getAddresses());
        user.setMobile(Long.parseLong(userSettingDto.getMobile()));
        user.setRole(userSettingDto.getRole());
        user.setDate_of_birth(userSettingDto.getDate_of_birth());
        user.setCountry(userSettingDto.getCountry());
        if (userSettingDto.getImage() != null && !userSettingDto.getImage().isEmpty()) { // Check if image is present
            String imageUrl = saveImage(userSettingDto.getImage());// Save the image
            user.setImage_url(imageUrl);// Set the image URL
        } else {
            user.setImage_url(null);
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserSettingDto userSettingDto) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userSettingDto.getFirstName());
            if (userSettingDto.getLastName() == null) {
                user.setLastName(null);
            }
            else {
                user.setLastName(userSettingDto.getLastName());
            }
            user.setEmail(userSettingDto.getEmail());
            user.setAddresses(userSettingDto.getAddresses());
            user.setMobile(Long.parseLong(userSettingDto.getMobile()));
            user.setDate_of_birth(userSettingDto.getDate_of_birth());
            user.setDate_of_birth(userSettingDto.getDate_of_birth());
            user.setCountry(userSettingDto.getCountry());

            if (userSettingDto.getImage() != null && !userSettingDto.getImage().isEmpty()) {//Similar to the createuser Check if the image is present
                String imageUrl = saveImage(userSettingDto.getImage());// saving the image and getting the image url
                user.setImage_url(imageUrl);// setting the image url
            }
            else {
                user.setImage_url(null);
            }

            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id " + id);
    }

    // Method to delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Method to get all users
    public List<UserSettingDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapEntityListToDTOList(users);
    }

    // Method to get a user by ID
    public UserSettingDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);// retrieving the user
        if (optionalUser.isPresent()) {// checking if the user is present
            return mapEntityToDTO(optionalUser.get()); //if the user is present return the user
        }
        throw new RuntimeException("User not found with id " + id);
    }

    public UserSettingDto mapEntityToDTO(User user) {//Converting the user entity to UserSettingDto to map the data
        UserSettingDto dto = new UserSettingDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAddresses(user.getAddresses());
        dto.setMobile(user.getMobile().toString());
        dto.setRole(user.getRole());
        dto.setDate_of_birth(user.getDate_of_birth());
        dto.setCountry(user.getCountry());
        dto.setImage_url(user.getImage_url());
        return dto;
    }


    public List<UserSettingDto> mapEntityListToDTOList(List<User> users) {//to convert List of User entities to List of UserSettingDto
        return users.stream().map(this::mapEntityToDTO).toList();
    }

    private String saveImage(MultipartFile image) throws IOException {// Method to save image and return the image URL or path
        String uploadDir = "src/main/resources/static/user-uploaded-images"; // It's our directory structure to store the images
        java.nio.file.Path path = java.nio.file.Paths.get(uploadDir);// Ensure the directory exists
        if (!java.nio.file.Files.exists(path)) {// Ensure the directory exists
            java.nio.file.Files.createDirectories(path);
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();// Generate a unique file name
        java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir, fileName);// Create the file path
        image.transferTo(filePath);// Save the image
        return "/static/user-uploaded-images/" + fileName;
    }
    // Add a new address to the user's address list
    public User addAddress(Long userId, String newAddress) {
        Optional<User> optionalUser = userRepository.findById(userId); // Fetch user by ID
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Retrieve the user
            user.getAddresses().add(newAddress); // Add the new address to the existing list
            return userRepository.save(user); // Save the updated user entity
        }
        throw new RuntimeException("User not found with id " + userId);
    }

    // Remove an address from the user's address list
    public User removeAddress(Long userId, String addressToRemove) {
        Optional<User> optionalUser = userRepository.findById(userId); // Fetch user by ID
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Retrieve the user
            user.getAddresses().remove(addressToRemove); // Remove the specified address from the list
            return userRepository.save(user); // Save the updated user entity
        }
        throw new RuntimeException("User not found with id " + userId);
    }

}
