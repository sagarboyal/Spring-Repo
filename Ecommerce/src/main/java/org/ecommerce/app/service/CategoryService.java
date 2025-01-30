package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getCategories();
    public void addCategory(Category category);
}
