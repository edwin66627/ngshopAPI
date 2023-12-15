package com.ngshop.repository;

import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryCustom {
    List<Product> searchProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO);
}
