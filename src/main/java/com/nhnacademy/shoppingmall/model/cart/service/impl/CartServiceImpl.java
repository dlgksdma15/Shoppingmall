package com.nhnacademy.shoppingmall.model.cart.service.impl;

import com.nhnacademy.shoppingmall.model.cart.domain.Cart;
import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.model.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.model.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addToCart(String userId, int productId, int quantity) {
        // 이미 장바구니에 있는지 확인
        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            int newQuantity = cart.getQuantity() + quantity;
            cartRepository.updateQuantity(cart.getCartId(), newQuantity);
            log.info("Updated cart item quantity: cartId={}, newQuantity={}", cart.getCartId(), newQuantity);
        } else {
            Cart cart = new Cart(0, userId, productId, quantity);
            cartRepository.save(cart);
            log.info("Added to cart: userId={}, productId={}, quantity={}", userId, productId, quantity);
        }
//        if(existingCart.isPresent()){
//            throw new RuntimeException("이미 장바구니에 담긴 상품입니다.");
//        }
//        Cart cart = new Cart(0,userId,productId,quantity);
//        cartRepository.save(cart);
//        log.info("Added to cart: userId={}, productId={}, quantity={}", userId, productId, quantity);

    }

    @Override
    public List<CartItem> getCartItems(String userId) {
        return cartRepository.findCartItemsByUserId(userId);
    }

    @Override
    public void updateCartItemQuantity(int cartId, int quantity) {
        cartRepository.updateQuantity(cartId,quantity);
        log.info("Updated cart item quantity: cartId={}, quantity={}", cartId, quantity);


    }

    @Override
    public void removeFromCart(int cartId) {
        cartRepository.deleteByCartId(cartId);
        log.info("Removed from cart: cartId={}", cartId);
    }

    @Override
    public void clearCart(String userId) {
        cartRepository.deleteAllByUserId(userId);
        log.info("Cleared cart for user: {}", userId);
    }
}
