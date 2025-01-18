package com.adverpix.ecommerce.service;

import com.adverpix.ecommerce.dto.CartDto;
import com.adverpix.ecommerce.entity.CartItem;
import com.adverpix.ecommerce.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartDto> getCartItems(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream().map(item -> {
            CartDto dto = new CartDto();
            dto.setId(item.getId());
            dto.setUserId(item.getUserId());
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setImage(item.getImage());
            return dto;
        }).toList();
    }

    public CartItem addCartItem(CartDto cartDto) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(cartDto.getUserId());
        cartItem.setProductId(cartDto.getProductId());
        cartItem.setProductName(cartDto.getProductName());
        cartItem.setQuantity(cartDto.getQuantity());
        cartItem.setPrice(cartDto.getPrice());
        cartItem.setImage(cartDto.getImage());
        return cartItemRepository.save(cartItem);
    }

    public void deleteByProductId(Integer productId) {
        Optional<CartItem> item = cartItemRepository.findByProductId(productId);
        item.ifPresent(cartItemRepository::delete);
    }

    public void clearCart(String userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(items);
    }
}

