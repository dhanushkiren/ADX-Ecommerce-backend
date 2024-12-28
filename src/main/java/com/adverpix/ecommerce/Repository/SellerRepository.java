package com.adverpix.ecommerce.Repository;

import com.adverpix.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // Find a seller by email
    Seller findByEmail(String email);

}
