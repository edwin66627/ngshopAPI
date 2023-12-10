package com.ngshop.service;

import com.ngshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts();
    ProductDTO getProduct(Long productID);
    ProductDTO createProduct(ProductDTO productDTO);
}
