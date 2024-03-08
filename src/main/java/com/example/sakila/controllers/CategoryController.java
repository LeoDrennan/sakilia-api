package com.example.sakila.controllers;

import com.example.sakila.entities.Category;
import com.example.sakila.input.CategoryInput;
import com.example.sakila.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Short id) {
        Category category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(category);
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@Validated @RequestBody CategoryInput data) {
        final var category = categoryService.createCategory(data);

        return ResponseEntity.ok(category);
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Short id, @RequestBody CategoryInput data) {
        final var updatedCategory = categoryService.updateCategory(id, data);

        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Short id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
