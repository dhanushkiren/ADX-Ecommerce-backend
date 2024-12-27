package com.example.sorting.controller;

import com.example.sorting.model.Product;
import com.example.sorting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // In-memory product list (Replace with DB in real apps)
    private static List<Product> productList = new ArrayList<>();

    static {
        productList.add(new Product(1, "Phone", 15000, "Electronics", "BrandA", 4.5, true));
        productList.add(new Product(2, "Laptop", 55000, "Electronics", "BrandB", 4.7, true));
        productList.add(new Product(3, "Shirt", 1500, "Fashion", "BrandC", 4.2, true));
        productList.add(new Product(4, "Shoes", 2000, "Fashion", "BrandD", 4.0, false));
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String sortBy,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) Double minPrice,
                                     @RequestParam(required = false) Double maxPrice) {
        return productService.filterAndSortProducts(productList, sortBy, category, minPrice, maxPrice);
    }
}
