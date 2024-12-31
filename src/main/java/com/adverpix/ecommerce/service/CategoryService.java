package com.adverpix.ecommerce.service;
import com.adverpix.ecommerce.repository.SellerRepository;
import com.adverpix.ecommerce.entity.Category;
import com.adverpix.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    // Create new category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Update existing category
    public Category updateCategory(int id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategory_name(categoryDetails.getCategory_name());
            category.setDescription(categoryDetails.getDescription());
            category.setSeller(categoryDetails.getSeller()); // Assuming you want to update the seller
            return categoryRepository.save(category);
        }
        return null;
    }

    // Delete category by ID
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}