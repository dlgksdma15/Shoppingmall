package com.nhnacademy.shoppingmall.model.order.repository;

import com.nhnacademy.shoppingmall.model.order.domain.Order;

import java.util.List;

public interface OrderRepository {
    int save(Order order);
    int getLastInsertId();
    List<Order> findByUserId(String userId, int offset, int limit);
    long countByUserId(String userId);
}