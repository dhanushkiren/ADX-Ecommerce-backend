package com.adverpix.ecommerce.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adverpix.ecommerce.entity.Seller;
import com.adverpix.ecommerce.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticateSeller(String email, String password) {
        Seller seller = sellerRepository.findByEmail(email);//Returns a seller by email(Unique value)
        if (seller != null) {
            // Here we encrypt the plain text and check if the encrypted text is equal to the hashed password
            return passwordEncoder.matches(password, seller.getPassword());//Returns true if the password is correct
        }
        return false;
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();//Returns a list of all the sellers
    }

    public Optional<Seller> getSellerById(int id) {
        return sellerRepository.findById(id);//Returns a seller by id
    }

    public Seller createSeller(Seller seller) {
        String encryptedPassword = passwordEncoder.encode(seller.getPassword());//Encrypts the password(WE use BCrypt to encrypt the password)
        seller.setPassword(encryptedPassword);//Sets the encrypted password
        return sellerRepository.save(seller);//creates a new seller
    }

    public Seller updateSeller(int id, Seller sellerDetails) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            Seller seller = optionalSeller.get();
            seller.setName(sellerDetails.getName());
            seller.setEmail(sellerDetails.getEmail());
            seller.setPassword(sellerDetails.getPassword());
            seller.setAddress(sellerDetails.getAddress());
            seller.setContact_number(sellerDetails.getContact_number());
            seller.setImage_url(sellerDetails.getImage_url());
            seller.setDescription(sellerDetails.getDescription());
            return sellerRepository.save(seller);// saves the updated seller
        }
        return null;
    }

    public void deleteSeller(int id) {
        sellerRepository.deleteById(id);// deletes the seller
    }
}
