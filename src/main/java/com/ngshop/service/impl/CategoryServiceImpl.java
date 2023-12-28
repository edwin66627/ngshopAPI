package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.CategoryDTO;
import com.ngshop.entity.Category;
import com.ngshop.mapper.CategoryMapper;
import com.ngshop.repository.CategoryRepository;
import com.ngshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> listCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Category", categoryId)));

        return categoryMapper.getCategoryDto(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.getCategory(categoryDTO);
        Category categorySaved = categoryRepository.save(category);
        return categoryMapper.getCategoryDto(categorySaved);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Category", categoryId)));

        category.setName(categoryDTO.getName());
        category.setColor(categoryDTO.getColor());
        category.setIcon(categoryDTO.getIcon());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Category", categoryId)));

        categoryRepository.deleteById(categoryId);
    }

}
