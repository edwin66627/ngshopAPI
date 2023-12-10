package com.ngshop.service;

import com.ngshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts();
    ProductDTO createProduct(ProductDTO productDTO);
}
