package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.ProductDto;
import com.adverpix.ecommerce.dto.ProductOverviewDTO;
import com.adverpix.ecommerce.dto.ProductRequestDTO;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.repository.ProductRepository;
import com.adverpix.ecommerce.repository.CategoryRepository;
import com.adverpix.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private static final String IMAGE_DIRECTORY = "static/uploaded-images/";
    private static final int MAX_IMAGES = 10;
    private static final List<String> ALLOWED_IMAGE_TYPES = List.of(
            "image/png", "image/jpeg", "image/jpg", "image/webp", "image/heif", "image/heic"
    );

    public List<ProductSummaryDTO> getProductSummaries() {
        return productRepository.findProductSummaries();
    }

    public ProductSummaryDTO getProductSummaryById(int id) {
        return productRepository.findProductSummaryById(id);
    }

    private void validateImageType(MultipartFile file) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));
        if (!ALLOWED_IMAGE_TYPES.contains(mimeType)) {
            throw new IllegalArgumentException("Invalid file type. Only PNG, JPEG, JPG, WebP, HEIF, and HEIC are allowed.");
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        validateImageType(image);
        Path directoryPath = Paths.get(IMAGE_DIRECTORY);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);
        return fileName;
    }

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

    public ProductSummaryDTO addProduct(ProductRequestDTO requestDTO) throws IOException {
        if (requestDTO.getImages() == null || requestDTO.getImages().isEmpty()) {
            throw new IllegalArgumentException("At least one image must be uploaded.");
        }

        List<String> imageUrls = saveImages(requestDTO.getImages());

        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice((int) requestDTO.getPrice());
        product.setStock(requestDTO.getStock());
        product.setRatingCount(requestDTO.getRatingCount());
        product.setImageUrl(String.join(",", imageUrls));

        if (!categoryRepository.existsById(requestDTO.getCategoryId()) || !sellerRepository.existsById(requestDTO.getSellerId())) {
            throw new IllegalArgumentException("Invalid category or seller.");
        }

        product.setCategory_id(categoryRepository.findById(requestDTO.getCategoryId()).get());
        product.setSeller_id(sellerRepository.findById(requestDTO.getSellerId()).get());

        // ✅ Updated line
        String categoryName = product.getCategory_id().getName().toLowerCase();
        switch (categoryName) {
            case "androidmobile":
            case "iosmobile":
                product.setProcessor(requestDTO.getProcessor());
                product.setRam(requestDTO.getRam());
                product.setStorage(requestDTO.getStorage());
                break;
            case "clothes":
                product.setFabric(requestDTO.getFabric());
                product.setFit(requestDTO.getFit());
                product.setPattern(requestDTO.getPattern());
                break;
            case "shoes":
                product.setMaterial(requestDTO.getMaterial());
                product.setClosureType(requestDTO.getClosureType());
                product.setSoleMaterial(requestDTO.getSoleMaterial());
                break;
        }

        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    public ProductSummaryDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return convertToResponseDTO(product);
    }

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

        if (!categoryRepository.existsById(requestDTO.getCategoryId()) || !sellerRepository.existsById(requestDTO.getSellerId())) {
            throw new IllegalArgumentException("Invalid category or seller.");
        }

        existingProduct.setCategory_id(categoryRepository.findById(requestDTO.getCategoryId()).get());
        existingProduct.setSeller_id(sellerRepository.findById(requestDTO.getSellerId()).get());

        // ✅ Updated line
        String categoryName = existingProduct.getCategory_id().getName().toLowerCase();
        switch (categoryName) {
            case "androidmobile":
            case "iosmobile":
                existingProduct.setProcessor(requestDTO.getProcessor());
                existingProduct.setRam(requestDTO.getRam());
                existingProduct.setStorage(requestDTO.getStorage());
                break;
            case "clothes":
                existingProduct.setFabric(requestDTO.getFabric());
                existingProduct.setFit(requestDTO.getFit());
                existingProduct.setPattern(requestDTO.getPattern());
                break;
            case "shoes":
                existingProduct.setMaterial(requestDTO.getMaterial());
                existingProduct.setClosureType(requestDTO.getClosureType());
                existingProduct.setSoleMaterial(requestDTO.getSoleMaterial());
                break;
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDTO(updatedProduct);
    }

    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        deleteImages(product.getImageUrl());
        productRepository.deleteById(id);
    }

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

    public List<ProductOverviewDTO> getAllProducts() {
        return productRepository.findAllProductsWithSellerAndCategory();
    }

    public List<ProductDto> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductDto convertToDTO(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setRatingCount(product.getRatingCount());
        dto.setReview(product.getReview());
        dto.setPrice(product.getPrice());
        dto.setOfferDetails(product.getOfferDetails());
        dto.setBrand(product.getBrand());
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }
}