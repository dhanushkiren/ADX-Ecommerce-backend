package com.adverpix.ecommerce.services;
import com.adverpix.ecommerce.Repository.SellerRepository;
import com.adverpix.ecommerce.Repository.CategoryRepository;
import com.adverpix.ecommerce.models.Product;
import com.adverpix.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private SellerRepository sellerRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();// get all products
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);// get product by id
    }

    public Product createProduct(Product product) {
        if (categoryRepository.existsById(product.getCategory().getCategory_id()) &&
                sellerRepository.existsById(product.getSeller().getSeller_id())) {// Check if the category and seller exist

            return productRepository.save(product);//create a new product only if the category and seller exist
        }
        throw new RuntimeException("Invalid category or seller ID.");
    }

    public Product updateProduct(int id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setRating_count(productDetails.getRating_count());
            product.setReview(productDetails.getReview());
            product.setOffer_details(productDetails.getOffer_details());
            product.setQuantity(productDetails.getQuantity());
            product.setImage_url(productDetails.getImage_url());
            product.setCategory(productDetails.getCategory());
            product.setSeller_name(productDetails.getSeller_name());
            product.setStock(productDetails.getStock());
            product.setNumber_of_reviews(productDetails.getNumber_of_reviews());
            return productRepository.save(product);// update product
        }
        return null;
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);// delete product
    }
}
