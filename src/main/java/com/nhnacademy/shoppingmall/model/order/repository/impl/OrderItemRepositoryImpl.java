package com.nhnacademy.shoppingmall.model.order.repository.impl;

import com.nhnacademy.shoppingmall.model.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.model.order.repository.OrderItemRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal.*;

public class OrderItemRepositoryImpl implements OrderItemRepository {
    @Override
    public int save(OrderItem orderItem) {
        Connection connection = getConnection();
        String sql = "INSERT INTO OrderItems (order_id, product_id, product_name, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderItem.getOrderId());
            psmt.setInt(2, orderItem.getProductId());
            psmt.setString(3, orderItem.getProductName());
            psmt.setInt(4, orderItem.getQuantity());
            psmt.setInt(5, orderItem.getUnitPrice());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            setSqlError(true);
            throw new RuntimeException("주문 상품 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<OrderItem> findByOrderId(int orderId) {
        Connection connection = getConnection();
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT order_item_id, order_id, product_id, product_name, quantity, unit_price FROM OrderItems WHERE order_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, orderId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem orderItem = new OrderItem(
                            rs.getInt("order_item_id"),
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getInt("unit_price")
                    );
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            setSqlError(true);
            throw new RuntimeException("주문 상품 조회 중 오류 발생", e);
        }
        return orderItems;
    }
}
