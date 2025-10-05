package com.nhnacademy.shoppingmall.model.cart.domain;

import java.util.Objects;

public class Cart {
    private int cartId; // cartId FK autoincrement
    private String userId; // FK
    private int productId; // FK
    private int quantity; // 수량

    public Cart(int cartId, String userId, int productId, int quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cart cart)) return false;
        return cartId == cart.cartId && productId == cart.productId && quantity == cart.quantity && Objects.equals(userId, cart.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, productId, quantity);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
