package com.nhnacademy.shoppingmall.model.order.service;

import com.nhnacademy.shoppingmall.model.order.domain.Order;
import com.nhnacademy.shoppingmall.model.order.domain.OrderItem;

import java.util.List;

public interface OrderService {
    void createOrder(String userId);
    List<Order> getOrdersByUserId(String userId, int page, int pageSize);
    List<OrderItem> getOrderItems(int orderId);
    long getTotalOrderCount(String userId);
}
