package com.example.sakila.services.interfaces;

import com.example.sakila.entities.Category;
import com.example.sakila.input.CategoryInput;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Short id);
    Category createCategory(CategoryInput data);
    Category updateCategory(Short id, CategoryInput data);
    void deleteCategory(Short id);
}
