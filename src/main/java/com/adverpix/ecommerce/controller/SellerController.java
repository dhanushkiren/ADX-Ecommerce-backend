package com.adverpix.ecommerce.controller;
import com.adverpix.ecommerce.entity.Seller;
import com.adverpix.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // Authenticate seller by email and password
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateSeller(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = sellerService.authenticateSeller(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Authentication successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // Get all sellers
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    // Get a seller by ID
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable int id) {
        Optional<Seller> seller = sellerService.getSellerById(id);
        return seller.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Create a new seller
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeller);
    }

    // Update an existing seller
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable int id, @RequestBody Seller sellerDetails) {
        try {
            Seller updatedSeller = sellerService.updateSeller(id, sellerDetails);
            return ResponseEntity.ok(updatedSeller);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a seller by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable int id) {
        try {
            sellerService.deleteSeller(id);
            return ResponseEntity.ok("Seller deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found with ID: " + id);
        }
    }
}