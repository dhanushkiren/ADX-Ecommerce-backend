package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Custom query to get product summaries
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(" +
            "p.id, p.name, p.description, p.price, p.stock, p.ratingCount, p.imageUrl, " +
            "p.category_id.category_id, p.seller_id.seller_id) " +
            "FROM Product p")
    List<ProductSummaryDTO> findProductSummaries();

    // Custom query to get a specific product summary by ID
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(" +
            "p.id, p.name, p.description, p.price, p.stock, p.ratingCount, p.imageUrl, " +
            "p.category_id.category_id, p.seller_id.seller_id) " +
            "FROM Product p WHERE p.id = :id")
    ProductSummaryDTO findProductSummaryById(int id);
}
