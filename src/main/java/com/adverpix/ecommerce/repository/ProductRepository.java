package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.entity.Product;
import com.adverpix.ecommerce.dto.ProductSummaryDTO;
import com.adverpix.ecommerce.dto.ProductOverviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    // Custom query to fetch product summaries
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(p.id, p.name, p.description, p.price, p.stock, p.ratingCount, p.imageUrl, p.category_id.category_id, p.seller_id.seller_id) " +
            "FROM Product p")
    List<ProductSummaryDTO> findProductSummaries();

    // Custom query to fetch a product summary by ID
    @Query("SELECT new com.adverpix.ecommerce.dto.ProductSummaryDTO(p.id, p.name, p.description, p.price, p.stock, p.ratingCount, p.imageUrl, p.category_id.category_id, p.seller_id.seller_id) " +
            "FROM Product p WHERE p.id = :id")
    ProductSummaryDTO findProductSummaryById(int id);


    @Query("SELECT new com.adverpix.ecommerce.dto.ProductOverviewDTO( " +
            "p.id, p.name, p.description, p.price, p.stock, p.imageUrl, " +
            "s.name AS sellerName, c.name AS categoryName) " +
            "FROM Product p " +
            "JOIN p.seller_id s " +
            "JOIN p.category_id c")
    List<ProductOverviewDTO> findAllProductsWithSellerAndCategory();

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProducts(@Param("query") String query);
}
