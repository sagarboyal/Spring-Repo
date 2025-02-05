package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty())
            throw new APIException("No Categories Found!!!");
        return categoryList;
    }

    @Override
    public void addCategory(Category category) {
        Category data = categoryRepository.findByCategoryName(category.getCategoryName());
        if (data != null) throw new APIException("Category already exists");
        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                            new ResourceNotFoundException("Category", "categoryId", id));
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
