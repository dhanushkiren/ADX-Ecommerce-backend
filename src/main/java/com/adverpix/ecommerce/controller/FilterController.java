package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.dto.ProductDto;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.repository.ProductRepository;
import com.adverpix.ecommerce.service.FilterService;
import com.adverpix.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class FilterController {

    @Autowired
    private FilterService filterService;
    @Autowired
    private ProductService productService;


    @GetMapping("/filter")
    public ResponseEntity<List<ProductSummaryDTO>> getFilteredProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Boolean availability,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Boolean stockAvailable,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Call the filterService method
        List<ProductSummaryDTO> products = filterService.getFilteredAndSortedProducts(
                name, minPrice, maxPrice, rating, availability, category,
                brand, stockAvailable, sortField, ascending, page, size);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public List<ProductDto> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }
}