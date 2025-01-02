package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.like(root1.get("name"), "%" + name + "%"));
            }
            if (minPrice != null) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.greaterThanOrEqualTo(root1.get("price"), minPrice));
            }
            if (maxPrice != null) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.lessThanOrEqualTo(root1.get("price"), maxPrice));
            }
            if (rating != null) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.greaterThanOrEqualTo(root1.get("rating"), rating));
            }
            if (availability != null) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.equal(root1.get("availability"), availability));
            }
            if (category != null) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.equal(root1.get("category_id").get("category_id"), category));
            }
            if (brand != null && !brand.isEmpty()) {
                result = result.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.like(root1.get("brand"), "%" + brand + "%"));
            }
            if (stockAvailable != null) {
                result = result.and((root1, query1, criteriaBuilder1) -> {
                    if (stockAvailable) {
                        return criteriaBuilder1.greaterThan(root1.get("stock"), 0);
                    } else {
                        return criteriaBuilder1.equal(root1.get("stock"), 0);
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
                .map(product -> new ProductSummaryDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getRatingCount(),
                        product.getImageUrl(),
                        product.getCategory_id().getCategory_id(),  // Corrected line
                        product.getSeller_id().getSeller_id()      // Corrected line
                ))
                .collect(Collectors.toList());
    }
}