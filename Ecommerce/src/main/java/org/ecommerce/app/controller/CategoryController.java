package org.ecommerce.app.controller;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.service.CategoryService;
import org.springframework.web.bind.annotation.*;

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
        return categoryService.getCategoryList();
    }

    @PostMapping("/public/categories")
    public String addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return "Category added successfully";
    }

    @DeleteMapping("/admin/categories/{id}")
    public String deleteCategory(@PathVariable Long id) {
        boolean data = categoryService.deleteCategoryById(id);
        return (data) ? "Category deleted successfully" : "Category id not found";
    }
}
