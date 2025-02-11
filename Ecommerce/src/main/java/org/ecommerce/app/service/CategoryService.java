package org.ecommerce.app.service;

import org.ecommerce.app.payload.category.CategoryDTO;
import org.ecommerce.app.payload.category.CategoryResponse;

public interface CategoryService {
    CategoryResponse getCategoryList(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO getCategoryById(long id);
    CategoryDTO createCategory(CategoryDTO categoryDto);
    CategoryDTO deleteCategoryById(long id);
    CategoryDTO updateCategory(CategoryDTO categoryDto);
}
