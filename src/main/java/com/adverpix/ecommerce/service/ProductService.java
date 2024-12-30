package com.adverpix.ecommerce.service;// This file is used to handle the business logic

import com.adverpix.ecommerce.dto.ProductRequestDTO;
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
import java.util.ArrayList; //This file is used to handle the array
import java.util.List; // This file is used to handle the list
import java.util.Objects;// This file is used to handle the object

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;
    public List<ProductSummaryDTO> getProductSummaries() {
        return productRepository.findProductSummaries();
    }

    public ProductSummaryDTO getProductSummaryById(int id) {
        return productRepository.findProductSummaryById(id);
    }
    private static final String IMAGE_DIRECTORY = "static/uploaded-images/"; // Directory for storing uploaded images
    private static final int MAX_IMAGES = 10; // Maximum number of images allowed

    // Allowed MIME types for image validation
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(// List of allowed image types
            "image/png",
            "image/jpeg",
            "image/jpg",
            "image/webp",
            "image/heif",
            "image/heic"//Only allows PNG, JPEG, JPG, WebP, HEIF, or HEIC formats.
    );

    /**
     * Validates the type of the uploaded file.
     * Only allows PNG, JPEG, JPG, WebP, HEIF, or HEIC formats.
     *
     * @param file The file to validate
     * @throws IllegalArgumentException if the file is not a valid image
     */
    private void validateImageType(MultipartFile file) throws IOException {//Validates the type of the uploaded file
        String mimeType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));//probeContentType is used to get the MIME type of the file
        if (!ALLOWED_IMAGE_TYPES.contains(mimeType)) { //Checking if the MIME type is allowed
            throw new IllegalArgumentException(
                    "Invalid file type. Only PNG, JPEG, JPG, WebP, HEIF, and HEIC are allowed."
            );
        }
    }

    // Save a single image
    private String saveImage(MultipartFile image) throws IOException {
        validateImageType(image); // Validate the image type

        Path directoryPath = Paths.get(IMAGE_DIRECTORY);//Get the directory path
        if (!Files.exists(directoryPath)) {//Check if the directory exists
            Files.createDirectories(directoryPath);//If the directory dosnt exists create it
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();//creating the unique image name
        Path filePath = directoryPath.resolve(fileName);//Creating the file path
        Files.copy(image.getInputStream(), filePath);//Copying the image to the file path
        return fileName;
    }

    // Save multiple images
    private List<String> saveImages(List<MultipartFile> images) throws IOException {
        if (images.size() > MAX_IMAGES) {
            throw new IllegalArgumentException("Cannot upload more than " + MAX_IMAGES + " images.");
        }

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String savedImage = saveImage(image);
            imageUrls.add(IMAGE_DIRECTORY + savedImage);
        }
        return imageUrls;
    }

    // Convert Product entity to ProductSummaryDTO
    private ProductSummaryDTO convertToResponseDTO(Product product) {
        ProductSummaryDTO responseDTO = new ProductSummaryDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStock(product.getStock());
        responseDTO.setRatingCount(product.getRatingCount());
        responseDTO.setImageUrls(product.getImageUrl());
        responseDTO.setCategoryId(product.getCategory_id().getCategory_id());
        responseDTO.setSellerId(product.getSeller_id().getSeller_id());
        return responseDTO;
    }

    // Create a new product
    public ProductSummaryDTO addProduct(ProductRequestDTO requestDTO) throws IOException {
        if (requestDTO.getImages() == null || requestDTO.getImages().isEmpty()) {
            throw new IllegalArgumentException("At least one image must be uploaded.");
        }

        List<String> imageUrls = saveImages(requestDTO.getImages());

        // Create and populate the Product entity
        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice((int) requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setRatingCount(requestDTO.getRatingCount());
        product.setImageUrl(String.join(",", imageUrls));

        // Validate category and seller
        if (categoryRepository.existsById(requestDTO.getCategoryId()) &&
                sellerRepository.existsById(requestDTO.getSellerId())) {
            product.setCategory_id(categoryRepository.findById(requestDTO.getCategoryId()).get());
            product.setSeller_id(sellerRepository.findById(requestDTO.getSellerId()).get());
        } else {
            throw new RuntimeException("Invalid category or seller ID.");
        }

        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    // Get product by ID
    public ProductSummaryDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return convertToResponseDTO(product);
    }

    // Update product details
    public ProductSummaryDTO updateProduct(int id, ProductRequestDTO requestDTO) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        if (requestDTO.getImages() != null && !requestDTO.getImages().isEmpty()) {
            List<String> newImageUrls = saveImages(requestDTO.getImages());
            existingProduct.setImageUrl(String.join(",", newImageUrls));
        }

        existingProduct.setName(requestDTO.getName());
        existingProduct.setDescription(requestDTO.getDescription());
        existingProduct.setPrice((int) requestDTO.getPrice());
        existingProduct.setStock(requestDTO.getStock());
        existingProduct.setRatingCount(requestDTO.getRatingCount());

        if (categoryRepository.existsById(requestDTO.getCategoryId()) &&
                sellerRepository.existsById(requestDTO.getSellerId())) {
            existingProduct.setCategory_id(categoryRepository.findById(requestDTO.getCategoryId()).get());
            existingProduct.setSeller_id(sellerRepository.findById(requestDTO.getSellerId()).get());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDTO(updatedProduct);
    }

    // Delete a product
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        deleteImages(product.getImageUrl());
        productRepository.deleteById(id);
    }

    // Delete associated images
    private void deleteImages(String imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String[] images = imageUrls.split(",");
            for (String image : images) {
                Path imagePath = Paths.get(image);
                try {
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    System.err.println("Error deleting image: " + image);
                }
            }
        }
    }

    // Get all products
    public List<ProductSummaryDTO> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductSummaryDTO> responseDTOs = new ArrayList<>();
        for (Product product : products) {
            responseDTOs.add(convertToResponseDTO(product));
        }
        return responseDTOs;
    }
}



