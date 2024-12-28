package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;
//Product-/-Category-/-Seller
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//Product-/-Category-/-Seller
    private String name;
    private String description;
    private int price;
    private int ratingCount;
    private String review;
    private String offerDetails;
    private int quantity;
    private String imageUrl;
    private String sellerName;
    private int stock;
    private int numberOfReviews;
    private String brand;
    private double rating;
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Seller seller;

}
