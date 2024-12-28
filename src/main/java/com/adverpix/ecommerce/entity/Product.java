package com.adverpix.ecommerce.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private int id;

    private String name;
    private String description;
    private int price;
    private int rating_count;
    private String review;
    private String offer_details;
    private int quantity;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "category_id") // Foreign key column for Category
    private Category category;

    private String seller_name;

    @ManyToOne
    @JoinColumn(name = "seller_id") // Foreign key column for Seller
    private Seller seller;

    private int stock;
    private int number_of_reviews;

    // Default constructor
    public Product() {}

    // Constructor with required fields
    public Product(String name, int price, String description, String image_url,
                   String review, String offer_details, String seller_name,
                   int stock, int rating_count, int number_of_reviews) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image_url = image_url;
        this.review = review;
        this.offer_details = offer_details;
        this.seller_name = seller_name;
        this.stock = stock;
        this.rating_count = rating_count;
        this.number_of_reviews = number_of_reviews;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(String offer_details) {
        this.offer_details = offer_details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getNumber_of_reviews() {
        return number_of_reviews;
    }

    public void setNumber_of_reviews(int number_of_reviews) {
        this.number_of_reviews = number_of_reviews;
    }
}
