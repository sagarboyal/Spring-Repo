package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @Override
    public void deleteCategoryById(long id) {
        Category category = getCategoryById(id);
        if (category != null)
            categoryRepository.delete(category);
    }

    @Override
    public void updateCategory(Category category) {
        getCategoryById(category.getCategoryId());
        categoryRepository.save(category);
    }

}
