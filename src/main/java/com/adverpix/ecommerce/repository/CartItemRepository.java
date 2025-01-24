package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(String userId);

    List<CartItem> findByUserIdAndProductId(String userId, Integer productId);

    void deleteByUserIdAndProductId(String userId, Integer productId);
}