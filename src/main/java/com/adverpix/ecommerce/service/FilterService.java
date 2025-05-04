package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    private ProductRepository productRepository;

    // Filtering and sorting implementation
    public List<ProductSummaryDTO> getFilteredAndSortedProducts(String name, Double minPrice, Double maxPrice,
                                                                Double rating, Boolean availability, Long category,
                                                                String brand, Boolean stockAvailable, String sortField,
                                                                boolean ascending, int page, int size) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            Specification<Product> result = Specification.where(null);

            if (name != null && !name.isEmpty()) {
                result = result.and((root1, query1, cb) ->
                        cb.like(root1.get("name"), "%" + name + "%"));
            }
            if (minPrice != null) {
                result = result.and((root1, query1, cb) ->
                        cb.greaterThanOrEqualTo(root1.get("price"), minPrice));
            }
            if (maxPrice != null) {
                result = result.and((root1, query1, cb) ->
                        cb.lessThanOrEqualTo(root1.get("price"), maxPrice));
            }
            if (rating != null) {
                result = result.and((root1, query1, cb) ->
                        cb.greaterThanOrEqualTo(root1.get("rating"), rating));
            }
            if (availability != null) {
                result = result.and((root1, query1, cb) ->
                        cb.equal(root1.get("availability"), availability));
            }
            if (category != null) {
                result = result.and((root1, query1, cb) ->
                        cb.equal(root1.get("category_id").get("category_id"), category));
            }
            if (brand != null && !brand.isEmpty()) {
                result = result.and((root1, query1, cb) ->
                        cb.like(root1.get("brand"), "%" + brand + "%"));
            }
            if (stockAvailable != null) {
                result = result.and((root1, query1, cb) -> {
                    if (stockAvailable) {
                        return cb.greaterThan(root1.get("stock"), 0);
                    } else {
                        return cb.equal(root1.get("stock"), 0);
                    }
                });
            }
            return result.toPredicate(root, query, criteriaBuilder);
        };

        // Sorting logic
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch filtered and sorted products
        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        // Map to ProductSummaryDTOs
        return productsPage.getContent().stream()
                .map(product -> {
                    ProductSummaryDTO dto = new ProductSummaryDTO();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setDescription(product.getDescription());
                    dto.setPrice(product.getPrice());
                    dto.setStock(product.getStock());
                    dto.setRatingCount(product.getRatingCount());
                    dto.setImageUrls(product.getImageUrl());
                    dto.setCategoryId(product.getCategory_id().getCategory_id());
                    dto.setSellerId(product.getSeller_id().getSeller_id());

                    // Set variant fields based on category
                    String categoryName = product.getCategory_id().getName().toLowerCase();
                    if (categoryName.contains("androidmobile") || categoryName.contains("iosmobile")) {
                        dto.setProcessor(product.getProcessor());
                        dto.setRam(product.getRam());
                        dto.setStorage(product.getStorage());
                    } else if (categoryName.contains("clothes")) {
                        dto.setFabric(product.getFabric());
                        dto.setFit(product.getFit());
                        dto.setPattern(product.getPattern());
                    } else if (categoryName.contains("shoes")) {
                        dto.setMaterial(product.getMaterial());
                        dto.setClosureType(product.getClosureType());
                        dto.setSoleMaterial(product.getSoleMaterial());
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }
}