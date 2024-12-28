package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.entity.Product;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface ProductService {
    List<Product> getAllProducts(); // Keep this existing method

    List<Product> filterAndSortProducts(String sortBy, String category, Double minPrice, Double maxPrice);
}