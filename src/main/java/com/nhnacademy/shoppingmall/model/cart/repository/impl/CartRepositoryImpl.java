package com.nhnacademy.shoppingmall.model.cart.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.model.cart.domain.Cart;
import com.nhnacademy.shoppingmall.model.cart.domain.CartItem;
import com.nhnacademy.shoppingmall.model.cart.repository.CartRepository;
import com.nhnacademy.shoppingmall.model.product.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartRepositoryImpl implements CartRepository {
    @Override
    public int save(Cart cart) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Cart(user_id, product_id, quantity) values (?,?,?)";

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, cart.getUserId());
            psmt.setInt(2, cart.getProductId());
            psmt.setInt(3, cart.getQuantity());

            int update = psmt.executeUpdate();
            return update;

        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 추가 중 오류 발생", e);
        }
    }

    @Override
    public int updateQuantity(int cartId, int quantity) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE Cart SET quantity = ? WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, quantity);
            psmt.setInt(2, cartId);

            return psmt.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 수량 수정 중 오류 발생", e);
        }
    }

    @Override
    public int deleteByCartId(int cartId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM Cart WHERE cart_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, cartId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 삭제 중 오류 발생", e);
        }
    }

    @Override
    public Optional<Cart> findByUserIdAndProductId(String userId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT cart_id, user_id, product_id, quantity FROM Cart WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setInt(2, productId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    Cart cart = new Cart(
                            rs.getInt("cart_id"),
                            rs.getString("user_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity")
                    );
                    return Optional.of(cart);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 조회 중 오류 발생", e);
        }
        return Optional.empty();
    }

    @Override
    public List<CartItem> findCartItemsByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<CartItem> cartItems = new ArrayList<>();

        String sql = """
            SELECT c.cart_id, c.user_id, c.product_id, c.quantity,
                   p.user_id as product_user_id, p.category_id, p.product_name, 
                   p.product_number, p.product_image, p.product_unit_cost, p.product_description
            FROM Cart c
            INNER JOIN Products p ON c.product_id = p.product_id
            WHERE c.user_id = ?
            """;

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_user_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_number"),
                            rs.getString("product_image"),
                            rs.getInt("product_unit_cost"),
                            rs.getString("product_description")
                    );

                    CartItem cartItem = new CartItem(
                            rs.getInt("cart_id"),
                            rs.getString("user_id"),
                            product,
                            rs.getInt("quantity")
                    );

                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 목록 조회 중 오류 발생", e);
        }
        return cartItems;
    }

    @Override
    public int deleteAllByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM Cart WHERE user_id = ?";

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("장바구니 전체 삭제 중 오류 발생", e);
        }
    }
}
