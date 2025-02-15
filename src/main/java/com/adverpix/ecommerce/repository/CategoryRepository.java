package com.adverpix.ecommerce.repository;

import com.adverpix.ecommerce.dto.CategoryResponseDto;
import com.adverpix.ecommerce.dto.CategorySummaryDto;
import com.adverpix.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT new com.adverpix.ecommerce.dto.CategorySummaryDto(" +
            "c.id, " +
            "(SELECT p.imageUrl FROM Product p WHERE p.category_id.id = c.id ORDER BY RAND() LIMIT 1), " +
            "(SELECT MIN(p.price) FROM Product p WHERE p.category_id.id = c.id), " +
            "c.name) " +
            "FROM Category c")
    List<CategorySummaryDto> getCategorySummaries();
    @Query("SELECT new com.adverpix.ecommerce.dto.CategoryResponseDto(" +
            "p.name, p.description, p.price, p.rating, p.review, " +
            "p.offerDetails, p.quantity, p.imageUrl, p.sellerName, " +
            "p.stock, p.numberOfReviews, p.brand) " +
            "FROM Product p WHERE p.category_id.id = :categoryId")
    List<CategoryResponseDto> findProductsByCategoryId(@Param("categoryId") int categoryId);
}
