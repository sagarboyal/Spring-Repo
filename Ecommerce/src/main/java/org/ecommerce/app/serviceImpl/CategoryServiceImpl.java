package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private long countCategory = 1L;

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        countCategory++;
        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(long id) {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .filter(category ->
                        category.getCategoryId()
                                .equals(id))
                .findFirst()
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
