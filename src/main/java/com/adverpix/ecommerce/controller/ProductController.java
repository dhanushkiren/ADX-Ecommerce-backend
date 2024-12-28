package com.adverpix.ecommerce.controller;
//Product-/-Category-/-Seller
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
  //Abinesh
    private static List<Product> productList = new ArrayList<>();

    static {
        productList.add(new Product(1, "Phone", 15000, "Electronics", "BrandA", 4.5, true));
        productList.add(new Product(2, "Laptop", 55000, "Electronics", "BrandB", 4.7, true));
        productList.add(new Product(3, "Shirt", 1500, "Fashion", "BrandC", 4.2, true));
        productList.add(new Product(4, "Shoes", 2000, "Fashion", "BrandD", 4.0, false));
    }
    @GetMapping("/filterlessget")
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

