package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categoryList = new ArrayList<Category>();
    private long countCategory = 1;

    @Override
    public List<Category> getCategoryList() {
        return categoryList;
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryList.stream()
                .filter(category ->
                        category.getCategoryId()
                                .equals(id))
                .findFirst()
                .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @Override
    public void addCategory(Category category) {
        category.setCategoryId(countCategory++);
        categoryList.add(category);
    }

    @Override
    public Boolean deleteCategoryById(long id) {
        Category category = getCategoryById(id);
        if (category != null)
            categoryList.remove(category);
        return (category != null);
    }

}
