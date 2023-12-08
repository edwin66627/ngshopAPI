package com.ngshop.service.impl;

import com.ngshop.dto.CategoryDTO;
import com.ngshop.entity.Category;
import com.ngshop.repository.CategoryRepository;
import com.ngshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> listCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToCategory(categoryDTO);
        Category categorySaved = categoryRepository.save(category);
        return convertToCategoryDTO(categorySaved);
    }

    private CategoryDTO convertToCategoryDTO(Category category){
        return modelMapper.map(category,CategoryDTO.class);
    }

    private Category convertToCategory(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }

}
