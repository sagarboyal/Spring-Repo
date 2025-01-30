package org.ecommerce.app.controller;

import org.ecommerce.app.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final List<Category> categories = new ArrayList<Category>();

    @GetMapping("/public/categories")
    public List<Category> getCategories() {
        return categories;
    }

    @PostMapping("/public/categories")
    public String addCategory(@RequestBody Category category) {
        categories.add(category);
        return "Category added successfully";
    }
}
