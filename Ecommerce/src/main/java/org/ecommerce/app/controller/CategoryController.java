package org.ecommerce.app.controller;

import jakarta.validation.Valid;
import org.ecommerce.app.payload.CategoryDTO;
import org.ecommerce.app.payload.CategoryResponse;
import org.ecommerce.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories() {
        return ResponseEntity.ok( categoryService.getCategoryList());
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category) {
        CategoryDTO data = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO data = categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/public/categories")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category) {
        CategoryDTO data = categoryService.updateCategory(category);
        return ResponseEntity.ok(data);
    }
}
