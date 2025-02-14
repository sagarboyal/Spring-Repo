package org.ecommerce.app.controller;

import jakarta.validation.Valid;
import org.ecommerce.app.config.AppConstants;
import org.ecommerce.app.payload.category.CategoryDTO;
import org.ecommerce.app.payload.category.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_CATEGORY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        return ResponseEntity.ok(categoryService.getCategoryList(pageNumber, pageSize, sortBy, sortOrder));
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
