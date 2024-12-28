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
    private int ratingCount;
    private String review;
    private String offerDetails;
    private int quantity;
    private String imageUrl;
    private String sellerName;
    private int stock;
    private int numberOfReviews;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category_id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller_id;
}
