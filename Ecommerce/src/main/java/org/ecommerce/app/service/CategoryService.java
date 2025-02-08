package org.ecommerce.app.service;

import org.ecommerce.app.payload.CategoryDTO;
import org.ecommerce.app.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getCategoryList(Integer pageNumber, Integer pageSize);
    CategoryDTO getCategoryById(long id);
    CategoryDTO createCategory(CategoryDTO categoryDto);
    CategoryDTO deleteCategoryById(long id);
    CategoryDTO updateCategory(CategoryDTO categoryDto);
}
