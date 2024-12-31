package com.adverpix.ecommerce.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EcommerceApplications {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplications.class, args);
    }

}

