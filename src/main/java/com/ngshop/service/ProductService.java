package com.ngshop.service;

import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.exception.domain.UnsupportedContentTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> listProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO);
    ProductDTO getProduct(Long productID);
    ProductDTO getProductWithCategory(Long productId);
    ProductDTO createProduct(ProductDTO productDTO, MultipartFile image);
    void updateProduct(ProductDTO productDTO, Long productID);
    void deleteProduct(Long productId);
    ProductStatisticsDTO getProductsCount();

}
