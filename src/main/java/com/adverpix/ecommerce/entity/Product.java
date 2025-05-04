package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int price;
    private Float ratingCount;
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

    // Android & iOS Mobile variants
    private String processor;
    private String ram;
    private String storage;

    // Clothes variants
    private String fabric;
    private String fit;
    private String pattern;

    // Shoes variants
    private String material;
    private String closureType;
    private String soleMaterial;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category_id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller_id;




}