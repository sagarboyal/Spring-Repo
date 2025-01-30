package org.ecommerce.app.controller;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully");
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategoryById(id);
        }catch (ResponseStatusException e){
            return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
        }
        return ResponseEntity.ok("Category deleted successfully");
    }
}
