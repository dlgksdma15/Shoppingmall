package com.nhnacademy.shoppingmall.model.order.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int orderId;
    private String userId;
    private int totalAmount;
    private Timestamp orderedAt;

    public Order(int orderId, String userId, int totalAmount, Timestamp orderedAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderedAt = orderedAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Timestamp orderedAt) {
        this.orderedAt = orderedAt;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Order order)) return false;
        return orderId == order.orderId && totalAmount == order.totalAmount && Objects.equals(userId, order.userId) && Objects.equals(orderedAt, order.orderedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, totalAmount, orderedAt);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderedAt=" + orderedAt +
                '}';
    }
}
