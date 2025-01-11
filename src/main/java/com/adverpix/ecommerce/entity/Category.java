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
    private String name;
    private String description;


    @ManyToOne// mapping category to seller
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "category_id", cascade = CascadeType.ALL, orphanRemoval = true)// mapping category to products
    private List<Product> products;

}
