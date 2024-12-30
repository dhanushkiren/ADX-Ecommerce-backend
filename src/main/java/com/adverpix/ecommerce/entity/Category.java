package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
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

    @OneToMany(mappedBy = "category_id", cascade = CascadeType.ALL, orphanRemoval = true)// mapping category to products
    private List<Product> products;

}
