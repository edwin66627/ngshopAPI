package com.ngshop.service;

import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductStatisticsDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts();
    ProductDTO getProduct(Long productID);
    ProductDTO createProduct(ProductDTO productDTO);
    void updateProduct(ProductDTO productDTO, Long productID);
    void deleteProduct(Long productId);
    ProductStatisticsDTO getProductsCount();
}
