package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.payload.CategoryResponse;

public interface CategoryService {
    public CategoryResponse getCategoryList();
    public Category getCategoryById(long id);
    public void addCategory(Category category);
    public void deleteCategoryById(long id);
    public void updateCategory(Category category);
}
