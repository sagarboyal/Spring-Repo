package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.payload.CategoryDTO;
import org.ecommerce.app.payload.CategoryResponse;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty())
            throw new APIException("No Categories Found!!!");

        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return CategoryResponse.builder()
                .content(categoryDTOList)
                .build();
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
