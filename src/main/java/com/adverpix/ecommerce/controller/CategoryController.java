package com.adverpix.ecommerce.controller;

import com.adverpix.ecommerce.entity.Category;
import com.adverpix.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")//It's the base URL
public class CategoryController {

    @Autowired // used to inject the CategoryService
    private CategoryService categoryService; // import the CategoryService

    @GetMapping // Handles only the Get request
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();// returns all the categories
    }

    @GetMapping("/{id}") // Handles only the Get request with a path variable(here the id is teh path variable)
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) { // Get the category by id
        Optional<Category> category = categoryService.getCategoryById(id); // returns the category
        return category.map(ResponseEntity::ok)//checks if the category is present
                .orElseGet(() -> ResponseEntity.notFound().build()); //if the category is not present returns not found
    }

    @PostMapping // Handles only the Post request
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);//  creates a new category
    }

    @PutMapping("/{id}")// Handles only the Put request(Update)
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);// updates the category
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build(); //checks if the category is updated and returns the updated category
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);// deletes the category
    }
}
