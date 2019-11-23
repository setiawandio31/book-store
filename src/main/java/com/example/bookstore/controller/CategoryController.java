package com.example.bookstore.controller;

import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;


    // Create
    @PostMapping("/create")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // Get All Notes
    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    // Get a Single Note
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return categoryRepository.findById(categoryId)
        .orElseThrow();
    }

    // Update a Note
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable(value = "id") Long categoryId,
                                            @Valid @RequestBody Category categoryDetails) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow();
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setCategoryDescription(categoryDetails.getCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory;
    }

    // Delete a Note
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow();

        categoryRepository.delete(category);

        return ResponseEntity.ok().build();
    }

}