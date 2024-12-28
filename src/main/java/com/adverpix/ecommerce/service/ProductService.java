package com.adverpix.ecommerce.service;// This file is used to handle the business logic

import com.adverpix.ecommerce.dto.ProductSummaryDTO;// This file is used to handle the product summary
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.repository.ProductRepository;
import com.adverpix.ecommerce.repository.CategoryRepository;
import com.adverpix.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;// This file is used to handle the business logic
import org.springframework.web.multipart.MultipartFile;//This file is used to upload images

import java.io.IOException;// used to handle the input and output of data
import java.nio.file.Files; //This file is used to handle the file system
import java.nio.file.Path; // This file is used to handle the single path
import java.nio.file.Paths; // this file is used to handle the multiple path of the files
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private static final String IMAGE_DIRECTORY = "static/uploaded-images/"; // Directory to store images

    // Save image to the directory
    private String saveImage(MultipartFile image) throws IOException {
        Path directoryPath = Paths.get(IMAGE_DIRECTORY);// Get the directory path
        if (!Files.exists(directoryPath)) {// Check if the directory exists
            Files.createDirectories(directoryPath);// Create the directory
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();// Generate a unique file name
        Path filePath = directoryPath.resolve(fileName);// Get the file path
        Files.copy(image.getInputStream(), filePath);// Copy the image to the directory
        return fileName;
    }

    // Create a new product with image
    public Product addProductWithImage(Product product, MultipartFile image) throws IOException {

        String imageUrl = saveImage(image);// Save the image

        // Set image URL in the product entity
        product.setImageUrl(IMAGE_DIRECTORY + imageUrl);

        // Check if category and seller are valid
        if (categoryRepository.existsById(product.getCategory().getCategory_id()) && // we use getCategory_id() two times since it is a foreign key
                sellerRepository.existsById(product.getSeller().getSeller_id())) {
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Invalid category or seller ID.");
        }
    }

    // Get product by ID
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Update a product
    public Product updateProduct(int id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    // Delete product by ID
    public void deleteProduct(int id) {
        if (productRepository.existsById(id)) {// Check if the product exists
            productRepository.deleteById(id);// Delete the product if the product exists
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    // Get all products
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductSummaryDTO> getProductSummaries() {// Get all product summaries using the DTO
        return productRepository.findProductSummaries();
    }

    public ProductSummaryDTO getProductSummaryById(int id) {//Get product summary by id using the dto
        return productRepository.findProductSummaryById(id);
    }


}

