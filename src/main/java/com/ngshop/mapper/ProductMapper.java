package com.ngshop.mapper;

import com.ngshop.dto.ProductDTO;
import com.ngshop.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "category.products", ignore = true)
    ProductDTO getProductDtoWithCategory(Product product);

    @Named("WithoutCategoryDto")
    @Mapping(target = "category", ignore = true)
    ProductDTO getProductDto(Product product);

    Product getProduct(ProductDTO productDTO);

}
