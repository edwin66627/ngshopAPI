package com.ngshop.service;

import com.ngshop.dto.CategoryDTO;
import com.ngshop.repository.CategoryRepository;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> listCategories();
}
