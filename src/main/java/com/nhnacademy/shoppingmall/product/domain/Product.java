package com.nhnacademy.shoppingmall.product.domain;

import java.util.Objects;

public class Product {
    private int productId; // PK
    private String userId; // FK
    private int categoryId; // FK
    private String productName;
    private String productNumber;
    private String productImage;
    private int productUnitCost;
    private String productDescription;

    public Product(int productId, String userId, int categoryId, String productName, String productNumber, String productImage, int productUnitCost, String productDescription) {
        this.productId = productId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productNumber = productNumber;
        this.productImage = productImage;
        this.productUnitCost = productUnitCost;
        this.productDescription = productDescription;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductUnitCost() {
        return productUnitCost;
    }

    public void setProductUnitCost(int productUnitCost) {
        this.productUnitCost = productUnitCost;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Product product)) return false;
        return productId == product.productId && categoryId == product.categoryId && productUnitCost == product.productUnitCost && Objects.equals(userId, product.userId) && Objects.equals(productName, product.productName) && Objects.equals(productNumber, product.productNumber) && Objects.equals(productImage, product.productImage) && Objects.equals(productDescription, product.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, categoryId, productName, productNumber, productImage, productUnitCost, productDescription);
    }

}
