package com.adverpix.ecommerce.service;
import com.adverpix.ecommerce.entity.Seller;
import com.adverpix.ecommerce.repository.SellerRepository;
import com.adverpix.ecommerce.entity.Category;
import com.adverpix.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adverpix.ecommerce.dto.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;

    // Get all categories
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    // Get category by ID
    public Optional<CategoryDto> getCategoryById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(this::mapEntityToDTO);
    }

    // Create new category
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Seller seller = sellerRepository.findById(categoryDto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + categoryDto.getSellerId()));

        Category category = mapDTOToEntity(categoryDto);
        category.setSeller(seller);

        Category savedCategory = categoryRepository.save(category);
        return mapEntityToDTO(savedCategory);
    }

    // Update existing category
    public CategoryDto updateCategory(int id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

        Seller seller = sellerRepository.findById(categoryDto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + categoryDto.getSellerId()));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setSeller(seller); // Update the seller relationship

        Category updatedCategory = categoryRepository.save(category);
        return mapEntityToDTO(updatedCategory);
    }
    // Convert Category to CategoryDto
    private CategoryDto mapEntityToDTO(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategory_id());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSellerId(category.getSeller() != null ? category.getSeller().getSeller_id() : null);
        return dto;
    }

    // Convert CategoryDto to Category
    private Category mapDTOToEntity(CategoryDto dto) {
        Category category = new Category();
        category.setCategory_id(dto.getCategoryId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    // Delete category by ID
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}