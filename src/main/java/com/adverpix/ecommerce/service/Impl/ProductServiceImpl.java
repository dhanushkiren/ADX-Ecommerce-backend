package com.adverpix.ecommerce.service.Impl;

import com.adverpix.ecommerce.entity.Product;
import org.springframework.data.domain.Sort;
import com.adverpix.ecommerce.repository.ProductRepository;
import com.adverpix.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> filterAndSortProducts(String sortBy, String category, Double minPrice, Double maxPrice) {
        // Determine sorting order
        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    sort = Sort.by(Sort.Direction.ASC, "price");
                    break;
                case "priceDesc":
                    sort = Sort.by(Sort.Direction.DESC, "price");
                    break;
                case "rating":
                    sort = Sort.by(Sort.Direction.DESC, "rating");
                    break;
            }
        }
        // Fetch filtered and sorted data
        return productRepository.findWithFilters(category, minPrice, maxPrice, sort);
    }
}