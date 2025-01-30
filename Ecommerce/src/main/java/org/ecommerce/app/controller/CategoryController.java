package org.ecommerce.app.controller;

import org.ecommerce.app.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
