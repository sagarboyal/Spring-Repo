package org.ecommerce.app.controller;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/public/categories")
    public String addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return "Category added successfully";
    }
}
