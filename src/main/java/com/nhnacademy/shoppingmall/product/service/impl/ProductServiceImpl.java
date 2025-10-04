package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product getProduct(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isPresent()){
            return productOptional.get();
        } else{
            throw  new ProductNotFoundException("Product not found with id: " + productId);
        }
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