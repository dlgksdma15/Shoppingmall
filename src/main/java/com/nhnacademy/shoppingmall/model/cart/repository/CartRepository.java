package com.nhnacademy.shoppingmall.model.cart.repository;

import com.nhnacademy.shoppingmall.model.cart.domain.Cart;
import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    /**
     * 장바구니에 상품 추가
     */
    int save(Cart cart);

    /**
     * 장바구니 아이템 수량 수정
     */
    int updateQuantity(int cartId, int quantity);

    /**
     * 장바구니 아이템 삭제
     */
    int deleteByCartId(int cartId);

    /**
     * 사용자의 특정 상품이 장바구니에 있는지 확인
     */
    Optional<Cart> findByUserIdAndProductId(String userId, int productId);

    /**
     * 사용자의 장바구니 목록 조회 (상품 정보 포함)
     */
    List<CartItem> findCartItemsByUserId(String userId);

    /**
     * 사용자의 모든 장바구니 아이템 삭제 (주문 완료 후)
     */
    int deleteAllByUserId(String userId);
}