package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.payload.category.CategoryDTO;
import org.ecommerce.app.payload.category.CategoryResponse;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategoryList(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder){
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categoryList = categoryPage.getContent();

        if (categoryList.isEmpty())
            throw new APIException("No Categories Found!!!");

        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        return CategoryResponse.builder()
                .content(categoryDTOList)
                .pageNumber(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .lastPage(categoryPage.isLast())
                .build();
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category data = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if (data != null) throw new APIException("Category already exists");
        // covert dto class to entity
        Category category = modelMapper.map(categoryDto, Category.class);
        return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(long id) {
        Category data = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                            new ResourceNotFoundException("Category", "categoryId", id));

        return modelMapper.map(data, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategoryById(long id) {
        Category category = modelMapper.map(getCategoryById(id), Category.class);
        if (category != null) categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto) {
        // if id not available it will throw exception
        getCategoryById(categoryDto.getCategoryId());
        // if found just covert it into entity class and update it save it
        categoryRepository.save(modelMapper.map(categoryDto, Category.class));
        return categoryDto;
    }
}
