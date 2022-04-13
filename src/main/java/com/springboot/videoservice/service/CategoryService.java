package com.springboot.videoservice.service;

import com.springboot.videoservice.model.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
}