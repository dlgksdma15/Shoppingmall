package com.nhnacademy.shoppingmall.model.cart.domain;

import com.nhnacademy.shoppingmall.model.product.domain.Product;

import java.util.Objects;

/**
 *  장바구니 아이템 (Cart + Product 정보)
 */
public class CartItem {
    private int cartId;
    private String userId;
    private Product product;
    private int quantity;

    public CartItem(int cartId, String userId, Product product, int quantity) {
        this.cartId = cartId;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public String getUserId() {
        return userId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CartItem cartItem)) return false;
        return cartId == cartItem.cartId && quantity == cartItem.quantity && Objects.equals(userId, cartItem.userId) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, product, quantity);
    }
    public int getTotalPrice() {
        return product.getProductUnitCost() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
