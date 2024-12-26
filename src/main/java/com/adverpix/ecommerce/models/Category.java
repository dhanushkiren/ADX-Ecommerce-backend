package com.adverpix.ecommerce.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private int category_id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String category_name;

    @Column(length = 500)
    private String description;

    @Column(name = "image_url", length = 255)
    private String image_url;

    @ManyToOne// mapping category to seller
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)// mapping category to products
    private List<Product> products;

    // Getters and Setters

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Corrected method name to match the field
    public Integer getId() {
        return category_id;
    }
}