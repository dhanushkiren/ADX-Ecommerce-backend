package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.entity.CartItem;
import com.adverpix.ecommerce.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItems(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCart(String userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(items);
    }

    public CartItem updateCartItem(String userId, Long itemId, CartItem updatedCartItem) {
        return cartItemRepository.findByUserIdAndId(userId, itemId)
                .map(existingItem -> {
                    existingItem.setQuantity(updatedCartItem.getQuantity());
                    return cartItemRepository.save(existingItem);
                }).orElseThrow(() -> new RuntimeException("Cart item not found for the given user"));
    }
}
