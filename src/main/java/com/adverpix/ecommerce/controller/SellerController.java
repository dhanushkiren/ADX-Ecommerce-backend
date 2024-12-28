package com.adverpix.ecommerce.controller;
import com.adverpix.ecommerce.models.Seller;
import com.adverpix.ecommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/login")
    public ResponseEntity<String> loginSeller(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = sellerService.authenticateSeller(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable int id) {
        Optional<Seller> seller = sellerService.getSellerById(id);
        return seller.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public Seller createSeller(@RequestBody Seller seller) {

        return sellerService.createSeller(seller);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable int id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.updateSeller(id, seller);
        return updatedSeller != null ? ResponseEntity.ok(updatedSeller) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteSeller(@PathVariable int id) {
        sellerService.deleteSeller(id);
    }
}
