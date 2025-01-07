package com.adverpix.ecommerce.entity;



import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items_list")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private double price;

    private String userId; // To associate cart items with a user
}

