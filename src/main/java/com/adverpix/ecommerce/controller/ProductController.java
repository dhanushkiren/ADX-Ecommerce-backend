package com.adverpix.ecommerce.controller;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProductWithImage(@RequestPart("product") Product product,
                                       @RequestPart("image") MultipartFile image) throws IOException {//This method is designed to handle HTTP requests for adding a product along with an associated image. Here’s what it does, part by part
        return productService.addProductWithImage(product, image);
    }

    // Get all products
    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    // Update product
    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    // Delete the product
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) { // delete the product using its id
        productService.deleteProduct(id);
    }
// This route will bring the specific product attributes from the DataBase using the DTO
    @GetMapping("/summaries")
    public ResponseEntity<List<ProductSummaryDTO>> getProductSummaries() {
        List<ProductSummaryDTO> productSummaries = productService.getProductSummaries();
        return ResponseEntity.ok(productSummaries);
    }

    @GetMapping("/summaries/{id}")
    public ResponseEntity<ProductSummaryDTO> getProductSummaryById(@PathVariable int id)
    {
        ProductSummaryDTO productSummary = productService.getProductSummaryById(id);
        return ResponseEntity.ok(productSummary);
    }
}
