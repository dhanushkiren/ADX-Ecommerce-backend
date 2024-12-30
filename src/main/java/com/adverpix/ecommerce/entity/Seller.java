package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seller_id;

    private String name;
    private String email;
    private String password;
    private String address;
    private String contact_number;
    private String image_url;
    private String description;

    @OneToMany(mappedBy = "seller_id", cascade = CascadeType.ALL)
    private List<Product> products;
}
