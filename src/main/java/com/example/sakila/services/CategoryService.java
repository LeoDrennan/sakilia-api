package com.example.sakila.services;

import com.example.sakila.entities.Category;
import com.example.sakila.input.CategoryInput;
import com.example.sakila.repositories.CategoryRepository;
import com.example.sakila.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    public Category getCategoryById(Short id) {

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category."));
    }

    public Category createCategory(CategoryInput data) {
        final var category = new Category();
        category.setName(data.getName());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Short id, CategoryInput data) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category."));

        if (data.getName() != null) {
            category.setName(data.getName());
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Short id) {
        final boolean categoryExists = categoryRepository.existsById(id);

        if (categoryExists) {
            categoryRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such category.");
        }
    }
}
