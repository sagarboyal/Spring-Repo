package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getCategoryList();
    public Category getCategoryById(long id);
    public void addCategory(Category category);
    public Boolean deleteCategoryById(long id);
}
