package com.adverpix.ecommerce.service;
import com.adverpix.ecommerce.Repository.SellerRepository;
import com.adverpix.ecommerce.entity.Category;
import com.adverpix.ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private SellerRepository sellerRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();//get all categories
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);//get category by id
    }

    public Category createCategory(Category category) {
        if(sellerRepository.existsById(category.getSeller().getSeller_id())){ //Check if the Seller exists
            return categoryRepository.save(category);//create a new category only if the seller exists
        }
        throw new RuntimeException("Invalid seller ID.");
    }

    public Category updateCategory(int id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategory_name(categoryDetails.getCategory_name());
            category.setDescription(categoryDetails.getDescription());
            category.setImage_url(categoryDetails.getImage_url());
            category.setSeller(categoryDetails.getSeller());
            return categoryRepository.save(category);//update the category
        }
        return null;
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);//delete the category
    }
}
