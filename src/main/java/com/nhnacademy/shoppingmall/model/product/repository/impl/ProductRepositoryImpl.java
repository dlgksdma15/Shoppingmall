package com.nhnacademy.shoppingmall.model.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        int result = 0;
        String sql = """
                insert into Products(product_id, user_id, category_id, product_name, product_number,
                product_image, product_unit_cost, product_description) values (?,?,?,?,?,?,?,?)
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1,product.getProductId());
            psmt.setString(2,product.getUserId());
            psmt.setInt(3,product.getCategoryId());
            psmt.setString(4,product.getProductName());
            psmt.setInt(5,product.getProductNumber());
            psmt.setString(6,product.getProductImage());
            psmt.setInt(7,product.getProductUnitCost());
            psmt.setString(8,product.getProductDescription());

            result = psmt.executeUpdate();


        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 등록 중 오류 발생: " + e.getClass().getName(), e);
        }

        return result;
    }

    @Override
    public int update(Product product) { // 상품 수정

        Connection connection = DbConnectionThreadLocal.getConnection();
        int result = 0;
        String sql = """
                update Products set product_name = ?, product_number = ?, product_image = ?,
                        product_unit_cost = ?, product_description = ?
                        where product_id = ?
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1,product.getProductName());
            psmt.setInt(2,product.getProductNumber());
            psmt.setString(3,product.getProductImage());
            psmt.setInt(4,product.getProductUnitCost());
            psmt.setString(5,product.getProductDescription());

            psmt.setInt(6, product.getProductId());

            result = psmt.executeUpdate();

        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 수정 중 오류 발생: " + e.getClass().getName(), e);
        }

        return result;
    }

    @Override
    public int deleteByProductId(long productId) { // 상품 삭제

        Connection connection = DbConnectionThreadLocal.getConnection();
        int result = 0;
        String sql = """
                delete from Products where product_id = ?
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setLong(1, productId);

            result = psmt.executeUpdate();

        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 삭제 중 오류 발생: " + e.getClass().getName(), e);
        }

        return result;
    }

    @Override
    public Optional<Product> findById(long productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = """
                select product_id, user_id, category_id, product_name, product_number,
                product_image, product_unit_cost, product_description from Products
                where product_id = ?
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setLong(1, productId);

            try(ResultSet rs = psmt.executeQuery()){
                if(rs.next()){
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("user_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_number"),
                            rs.getString("product_image"),
                            rs.getInt("product_unit_cost"),
                            rs.getString("product_description")
                    );
                    return Optional.of(product);
                }

            }

        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 조회 중 오류 발생: " + e.getClass().getName(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Product> products = new ArrayList<>();
        String sql = """
                select product_id, user_id, category_id, product_name, product_number,
                product_image, product_unit_cost, product_description from Products
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            try(ResultSet rs = psmt.executeQuery()){
                while(rs.next()){
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("user_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_number"),
                            rs.getString("product_image"),
                            rs.getInt("product_unit_cost"),
                            rs.getString("product_description")
                    );
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("모든 상품 조회 중 오류 발생: " + e.getClass().getName(), e);
        }
        return products;
    }

    @Override
    public List<Product> findLatestProducts(int offset, int limit) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        List<Product> products = new ArrayList<>();
        String sql = """
                select product_id, user_id, category_id, product_name, product_number,
                product_image, product_unit_cost, product_description from Products
                order by created_at desc limit ?, ?
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            try(ResultSet rs = psmt.executeQuery()){
                while(rs.next()){
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("user_id"),
                            rs.getInt("category_id"),
                            rs.getString("product_name"),
                            rs.getInt("product_number"),
                            rs.getString("product_image"),
                            rs.getInt("product_unit_cost"),
                            rs.getString("product_description")
                    );
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("최신 상품 조회 중 오류 발생: " + e.getClass().getName(), e);
        }
        return products;
    }

    @Override
    public long totalCount() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        long count = 0;
        String sql = "select count(*) from Products";
        log.debug("SQL: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("상품 개수 조회 중 오류 발생: " + e.getClass().getName(), e);
        }
        return count;
    }
}
