package com.nhnacademy.shoppingmall.model.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.model.order.domain.Order;
import com.nhnacademy.shoppingmall.model.order.repository.OrderRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int save(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO Orders (user_id, total_amount, ordered_at) VALUES (?, ?, ?)";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, order.getUserId());
            psmt.setInt(2, order.getTotalAmount());
            psmt.setTimestamp(3, order.getOrderedAt());

            return psmt.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("주문 생성 중 오류 발생", e);
        }
    }

    @Override
    public int getLastInsertId() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT LAST_INSERT_ID()";

        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("주문 ID 조회 중 오류 발생", e);
        }
        return 0;
    }

    @Override
    public List<Order> findByUserId(String userId, int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, user_id, total_amount, ordered_at FROM Orders WHERE user_id = ? ORDER BY ordered_at DESC LIMIT ?, ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("order_id"),
                            rs.getString("user_id"),
                            rs.getInt("total_amount"),
                            rs.getTimestamp("ordered_at")
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("주문 목록 조회 중 오류 발생", e);
        }
        return orders;
    }

    @Override
    public long countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT COUNT(*) FROM Orders WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("주문 개수 조회 중 오류 발생", e);
        }
        return 0;
    }
}
