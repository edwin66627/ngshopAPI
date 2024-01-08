package com.ngshop.mapper;

import com.ngshop.dto.CategoryDTO;
import com.ngshop.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(uses = ProductMapper.class, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "products", qualifiedByName = "WithoutCategoryDto")
    CategoryDTO getCategoryDto(Category category);

    Category getCategory(CategoryDTO categoryDTO);

}
