package com.nhnacademy.shoppingmall.category.service;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryService {
    Category getCategory(int categoryId);
    List<Category> getAllCategories();
    void createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int categoryId);
}
