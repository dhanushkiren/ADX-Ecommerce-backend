package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(p.name, p.price, p.ratingCount, p.imageUrl, p.stock, p.numberOfReviews) FROM Product p")
    List<ProductSummaryDTO> findProductSummaries();
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(p.name, p.price, p.ratingCount, p.imageUrl, p.stock, p.numberOfReviews) FROM Product p WHERE p.id = :id")
    ProductSummaryDTO findProductSummaryById(Integer id);

}
