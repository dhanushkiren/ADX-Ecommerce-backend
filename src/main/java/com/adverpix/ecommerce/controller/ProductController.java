package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        if (sortBy == null && category == null && minPrice == null && maxPrice == null) {
            return productService.getAllProducts();
        }
        return productService.filterAndSortProducts(sortBy, category, minPrice, maxPrice);
    }
}