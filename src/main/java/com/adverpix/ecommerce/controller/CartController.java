package com.adverpix.ecommerce.controller;


import com.adverpix.ecommerce.entity.CartItem;
import com.adverpix.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartItem> addCartItem(@PathVariable String userId, @RequestBody CartItem cartItem) {
        cartItem.setUserId(userId); // Associate userId with the cart item
        return ResponseEntity.ok(cartService.addCartItem(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/update/{itemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable String userId,
            @PathVariable Long itemId,
            @RequestBody CartItem updatedCartItem) {

        CartItem updatedItem = cartService.updateCartItem(userId, itemId, updatedCartItem);
        return ResponseEntity.ok(updatedItem);
    }

}