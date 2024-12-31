package com.adverpix.ecommerce.service;
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
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    // Get a seller by ID
    public Optional<Seller> getSellerById(int id) {
        return sellerRepository.findById(id);
    }

    // Create a new seller
    public Seller createSeller(Seller seller) {
        // Encrypt the seller's password
        String encryptedPassword = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(encryptedPassword);
        return sellerRepository.save(seller);
    }

    // Update an existing seller
    public Seller updateSeller(int id, Seller sellerDetails) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            Seller seller = optionalSeller.get();
            seller.setName(sellerDetails.getName());
            seller.setEmail(sellerDetails.getEmail());
            // Only encrypt the password if it's changed
            if (sellerDetails.getPassword() != null && !sellerDetails.getPassword().isEmpty()) {
                String encryptedPassword = passwordEncoder.encode(sellerDetails.getPassword());
                seller.setPassword(encryptedPassword);
            }
            seller.setAddress(sellerDetails.getAddress());
            seller.setContact_number(sellerDetails.getContact_number());
            seller.setDescription(sellerDetails.getDescription());
            return sellerRepository.save(seller);
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
