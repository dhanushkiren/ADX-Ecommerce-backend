package com.adverpix.ecommerce.controller;
import com.adverpix.ecommerce.dto.ProductRequestDTO;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // Endpoint to get all product summaries
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Endpoint to get a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductSummaryDTO> getProductById(@PathVariable int id) {
        ProductSummaryDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Endpoint to add a new product
    @PostMapping
    public ResponseEntity<ProductSummaryDTO> addProduct(@ModelAttribute ProductRequestDTO productRequestDTO) throws IOException {
        ProductSummaryDTO product = productService.addProduct(productRequestDTO);
        return ResponseEntity.ok(product);
    }

    // Endpoint to update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductSummaryDTO> updateProduct(@PathVariable int id, @ModelAttribute ProductRequestDTO productRequestDTO) throws IOException {
        ProductSummaryDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Endpoint to delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
