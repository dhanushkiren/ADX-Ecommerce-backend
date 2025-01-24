package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(nullable = false)
    private String userId; // User Identifier

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Join column for Product entity
    private Product product; // Association with Product entity

    @Column(nullable = false)
    private int quantity; // Quantity of the product
}
