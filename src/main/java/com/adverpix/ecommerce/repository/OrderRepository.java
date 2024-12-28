package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.entity.Order;
import com.adverpix.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}