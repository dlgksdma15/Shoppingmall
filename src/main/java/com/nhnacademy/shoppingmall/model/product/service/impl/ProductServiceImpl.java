package com.nhnacademy.shoppingmall.model.product.service.impl;

import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.model.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.model.product.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product getProduct(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteByProductId(Long.parseLong(productId));
    }
}
