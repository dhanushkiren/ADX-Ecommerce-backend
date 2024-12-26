package com.adverpix.ecommerce.services;

import com.adverpix.ecommerce.models.Seller;
import com.adverpix.ecommerce.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();//Returns a list of all the sellers
    }

    public Optional<Seller> getSellerById(int id) {
        return sellerRepository.findById(id);//Returns a seller by id
    }

    public Seller createSeller(Seller seller) {
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