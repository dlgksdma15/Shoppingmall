package com.nhnacademy.shoppingmall.model.product.service;

import com.nhnacademy.shoppingmall.model.product.domain.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(int productId);

    List<Product> getProducts();

//    List<Product> getProducts(int page, int pageSize);
// 페이징 추가
    List<Product> getProductsWithPaging(int offset, int limit);
    long getTotalProductCount();

    long getProductCount();

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(String productId);

}