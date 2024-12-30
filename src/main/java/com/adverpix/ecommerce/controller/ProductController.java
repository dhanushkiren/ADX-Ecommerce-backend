package com.adverpix.ecommerce.controller;
import com.adverpix.ecommerce.dto.ProductRequestDTO;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;// this import is to handle the HTTP status codes
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;// this import is used to handle the multipart file

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create product with image
    @PostMapping("/add")
    public ResponseEntity<ProductSummaryDTO> addProduct(
            @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            productRequestDTO.setImages(images);
            ProductSummaryDTO createdProduct = productService.addProduct(productRequestDTO);
            return ResponseEntity.ok(createdProduct);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductSummaryDTO>> getAllProducts() {
        List<ProductSummaryDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductSummaryDTO> getProductById(@PathVariable int id) {
        try {
            ProductSummaryDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<ProductSummaryDTO> updateProduct(
            @PathVariable int id,
            @RequestPart("product") ProductRequestDTO productRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            if (images != null) {
                productRequestDTO.setImages(images);
            }
            ProductSummaryDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

