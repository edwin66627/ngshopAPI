package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.entity.Product;
import com.ngshop.mapper.ProductMapper;
import com.ngshop.repository.ProductRepository;
import com.ngshop.repository.ProductRepositoryCustom;
import com.ngshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductRepositoryCustom productRepositoryCustom;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> listProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO) {
        List<Product> products = productRepositoryCustom.searchProducts(productSearchCriteriaDTO);
        return products.stream().map(productMapper::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", productId)));
        return this.productMapper.getProductDto(product);
    }

    @Override
    public ProductDTO getProductWithCategory(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        ProductDTO dto = productMapper.getProductDtoWithCategory(product.get());
        return dto;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = this.productMapper.getProduct(productDTO);
        Product productSaved = productRepository.save(product);
        return this.productMapper.getProductDto(productSaved);
    }

    @Override
    public void updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", productId)));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setBrand(productDTO.getBrand());
        product.setImage(productDTO.getImage());
        product.setPrice(productDTO.getPrice());
        product.setCountInStock(productDTO.getCountInStock());
        product.setFeatured(productDTO.isFeatured());
        product.setRichDescription(productDTO.getRichDescription());
        product.getCategory().setId(productDTO.getCategory().getId());
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", productId)));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductStatisticsDTO getProductsCount() {
        ProductStatisticsDTO productStatisticsDTO = new ProductStatisticsDTO();
        productStatisticsDTO.setTotalCount(productRepository.count());
        return productStatisticsDTO;
    }

}
