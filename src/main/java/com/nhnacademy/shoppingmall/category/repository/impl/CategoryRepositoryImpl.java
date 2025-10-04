package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public int save(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "INSERT INTO Categories (category_id, category_name) VALUES (?, ?)";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, category.getCategoryId());
            psmt.setString(2, category.getCategoryName());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "UPDATE Categories SET category_name = ? WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, category.getCategoryName());
            psmt.setInt(2, category.getCategoryId());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByCategoryId(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            return psmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Categories WHERE category_id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, categoryId);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "SELECT * FROM Categories";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement psmt = connection.prepareStatement(sql); ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}