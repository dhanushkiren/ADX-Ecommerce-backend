package com.adverpix.ecommerce.service;
import com.adverpix.ecommerce.dto.SellerDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adverpix.ecommerce.entity.Seller;
import com.adverpix.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Ensure this is a Spring bean

    // Authenticate seller by email and password
    public boolean authenticateSeller(String email, String password) {
        Seller seller = sellerRepository.findByEmail(email); // Assuming a custom query method exists in the repository
        if (seller != null) {
            // Match the entered password with the hashed password
            return passwordEncoder.matches(password, seller.getPassword());
        }
        return false;
    }

    // Get all sellers
    public List<SellerDto> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return mapEntityListToDTOList(sellers);
    }
    public SellerDto mapEntityToDTO(Seller seller) {//Converting the user entity to UserSettingDto to map the data
        SellerDto dto = new SellerDto();
        dto.setId(seller.getSeller_id());
        dto.setName(seller.getName());
        dto.setEmail(seller.getEmail());
        dto.setAddress(seller.getAddress());
        dto.setContact_number(seller.getContact_number());
        dto.setDescription(seller.getDescription());
        return dto;
    }
    private Seller mapDTOToEntity(SellerDto dto) {
        Seller seller = new Seller();
        seller.setSeller_id(dto.getId());
        seller.setName(dto.getName());
        seller.setEmail(dto.getEmail());
        seller.setAddress(dto.getAddress());
        seller.setContact_number(dto.getContact_number());
        seller.setDescription(dto.getDescription());
        return seller;
    }


    public List<SellerDto> mapEntityListToDTOList(List<Seller> sellers) {//to convert List of User entities to List of UserSettingDto
        return sellers.stream().map(this::mapEntityToDTO).toList();
    }

    // Get a seller by ID
    public Optional<SellerDto> getSellerById(int id) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        return optionalSeller.map(this::mapEntityToDTO);
    }

    // Create a new seller
    public Seller createSeller(Seller seller) {
        // Encrypt the seller's password
        String encryptedPassword = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(encryptedPassword);
        return sellerRepository.save(seller);
    }

    // Update an existing seller
    public SellerDto updateSeller(int id, SellerDto sellerDto) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            Seller seller = optionalSeller.get();
            seller.setName(sellerDto.getName());
            seller.setEmail(sellerDto.getEmail());
            seller.setAddress(sellerDto.getAddress());
            seller.setContact_number(sellerDto.getContact_number());
            seller.setDescription(sellerDto.getDescription());

            Seller updatedSeller = sellerRepository.save(seller);
            return mapEntityToDTO(updatedSeller);
        }
        throw new RuntimeException("Seller not found with ID: " + id);
    }

    // Delete a seller by ID
    public void deleteSeller(int id) {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Seller not found with ID: " + id);
        }
    }
}
