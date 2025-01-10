package com.adverpix.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Order> orders;
    private String username;
    private String firstName;
    private String email;
    private List<String> addresses = new ArrayList<>();
    private String lastName;
    private String mobile;
    private String password;
    private String image_url;
    private String role;
    private String Country;
    @Temporal(TemporalType.DATE)
    private Date date_of_birth;
}
