package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.UserSettingDto;
import com.adverpix.ecommerce.entity.User;
import com.adverpix.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserSettingsService {

    @Autowired
    private UserRepository userRepository;
    private static final String IMAGE_DIRECTORY = "static/uploaded-images/";
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            "image/png", "image/jpeg", "image/jpg", "image/webp", "image/heif", "image/heic"
    );
    private void validateImageType(MultipartFile file) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));
        if (!ALLOWED_IMAGE_TYPES.contains(mimeType)) {
            throw new IllegalArgumentException("Invalid file type. Only PNG, JPEG, JPG, WebP, HEIF, and HEIC are allowed.");
        }
    }
    private List<String> saveImages(List<MultipartFile> images) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String savedImage = saveImage(image);
            imageUrls.add(IMAGE_DIRECTORY + savedImage);
        }
        return imageUrls;
    }
    private String saveImage(MultipartFile image) throws IOException {
        validateImageType(image);
        Path directoryPath = Paths.get(IMAGE_DIRECTORY);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);
        return fileName;
    }

    public User createUser(UserSettingDto userSettingDto) throws IOException {
        if (userSettingDto.getImages() == null || userSettingDto.getImages().isEmpty()) {
            throw new IllegalArgumentException("At least one image must be uploaded.");
        }

        List<String> imageUrls = saveImages(userSettingDto.getImages());
        User user = new User();
        user.setUsername(userSettingDto.getUsername());
        user.setFirstName(userSettingDto.getFirstName());
        user.setLastName(userSettingDto.getLastName() != null ? userSettingDto.getLastName() : "");
        user.setEmail(userSettingDto.getEmail());
        user.setPassword(userSettingDto.getPassword());
        user.setAddresses(userSettingDto.getAddresses());
        user.setMobile(userSettingDto.getMobile());
        user.setRole(userSettingDto.getRole());
        user.setDate_of_birth(userSettingDto.getDate_of_birth());
        user.setCountry(userSettingDto.getCountry());
        user.setImageUrl(String.join(",", imageUrls));
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserSettingDto userSettingDto) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userSettingDto.getFirstName());
            user.setLastName(userSettingDto.getLastName() != null ? userSettingDto.getLastName() : null);
            user.setEmail(userSettingDto.getEmail());
            user.setAddresses(userSettingDto.getAddresses() != null ? userSettingDto.getAddresses() : user.getAddresses());
            user.setMobile(userSettingDto.getMobile());
            user.setPassword(userSettingDto.getPassword() != null ? userSettingDto.getPassword() : user.getPassword());
            user.setDate_of_birth(userSettingDto.getDate_of_birth());
            user.setCountry(userSettingDto.getCountry());

            if (userSettingDto.getImages() != null && !userSettingDto.getImages().isEmpty()) {
                List<String> newImageUrls = saveImages(userSettingDto.getImages());
                user.setImageUrl(String.join(",", newImageUrls));
            }

            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id " + id);
    }

    // Method to delete a user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        deleteImages(userRepository.findById(id).get().getImageUrl());
        userRepository.deleteById(id);
    }
    private void deleteImages(String imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String[] images = imageUrls.split(",");
            for (String image : images) {
                Path imagePath = Paths.get(image);
                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    System.err.println("Error deleting image: " + image);
                }
            }
        }
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
    // Add a new email to the user's address list
    public User updateEmail(Long userId, String newEmail) {
        Optional<User> optionalUser = userRepository.findById(userId); // Fetch user by ID
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Retrieve the user
            user.setEmail(newEmail); // Updates the email
            return userRepository.save(user); // Save the updated user entity
        }
        throw new RuntimeException("User not found with id " + userId);
    }
    public User updateNumber(Long userId, String newNumber) {
        Optional<User> optionalUser = userRepository.findById(userId); // Fetch user by ID
        if (optionalUser.isPresent()) {
            User user = optionalUser.get(); // Retrieve the user
            user.setMobile(newNumber); // Updates the email
            return userRepository.save(user); // Save the updated user entity
        }
        throw new RuntimeException("User not found with id " + userId);
    }
//    public User updateImage(Long id, MultipartFile image) {
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isPresent()) {
//            if (userSettingDto.getImages() != null && !userSettingDto.getImages().isEmpty()) {
//                List<String> newImageUrls = saveImages(userSettingDto.getImages());
//                user.setImageUrl(String.join(",", newImageUrls));
//            }
//            return userRepository.save(user);
//        } else {
//            throw new RuntimeException("User not found with id " + id);
//        }
//    }


    public UserSettingDto mapEntityToDTO(User user) {//Converting the user entity to UserSettingDto to map the data
        UserSettingDto dto = new UserSettingDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAddresses(user.getAddresses());
        dto.setMobile(user.getMobile());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setDate_of_birth(user.getDate_of_birth());
        dto.setCountry(user.getCountry());
        dto.setImageUrls(user.getImageUrl());
        return dto;
    }


    public List<UserSettingDto> mapEntityListToDTOList(List<User> users) {//to convert List of User entities to List of UserSettingDto
        return users.stream().map(this::mapEntityToDTO).toList();
    }

}
