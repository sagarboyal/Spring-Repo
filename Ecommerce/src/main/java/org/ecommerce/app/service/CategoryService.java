package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getCategoryList();
    public Category getCategoryById(long id);
    public void addCategory(Category category);
    public void deleteCategoryById(long id);
    public void updateCategory(Category category);
}
