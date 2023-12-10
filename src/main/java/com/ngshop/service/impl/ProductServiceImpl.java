package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.entity.Category;
import com.ngshop.entity.Product;
import com.ngshop.repository.CategoryRepository;
import com.ngshop.repository.ProductRepository;
import com.ngshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        Product productSaved = productRepository.save(product);
        return convertToProductDTO(productSaved);
    }

    private ProductDTO convertToProductDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToProduct(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

}
