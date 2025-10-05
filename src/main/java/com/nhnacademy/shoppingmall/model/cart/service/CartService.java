package com.nhnacademy.shoppingmall.model.cart.service;

import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;

import java.util.List;

public interface CartService {
    void addToCart(String userId, int productId, int quantity);
    List<CartItem> getCartItems(String userId);
    void updateCartItemQuantity(int cartId, int quantity);
    void removeFromCart(int cartId);
    void clearCart(String userId);
}

