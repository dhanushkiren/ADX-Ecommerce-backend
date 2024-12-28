package com.adverpix.ecommerce.service.Impl;

import com.adverpix.ecommerce.entity.Product;
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
}
