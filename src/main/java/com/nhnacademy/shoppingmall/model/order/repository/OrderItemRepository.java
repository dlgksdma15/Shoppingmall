package com.nhnacademy.shoppingmall.model.order.repository;

import com.nhnacademy.shoppingmall.model.order.domain.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    int save(OrderItem orderItem);
    List<OrderItem> findByOrderId(int orderId);
}
