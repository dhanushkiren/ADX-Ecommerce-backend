
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
    private String userId; 

    @Column(nullable = false)
    private Integer productId; 

    @Column(nullable = false)
    private String productName; 

    @Column(nullable = false)
    private int quantity; 

    @Lob
    private byte[] image; 

    @Column(nullable = false)
    private double price; 
}




