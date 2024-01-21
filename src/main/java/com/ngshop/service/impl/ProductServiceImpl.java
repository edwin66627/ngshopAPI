package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.entity.Product;
import com.ngshop.exception.domain.UnsupportedContentTypeException;
import com.ngshop.mapper.ProductMapper;
import com.ngshop.repository.ProductRepository;
import com.ngshop.repository.ProductRepositoryCustom;
import com.ngshop.service.ProductService;
import com.ngshop.utils.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductRepositoryCustom productRepositoryCustom;
    private final FileStorage fileStorage;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom,
                              FileStorage fileStorage, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
        this.fileStorage = fileStorage;
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
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id",productId)));
        return this.productMapper.getProductDto(product);
    }

    @Override
    public ProductDTO getProductWithCategory(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        ProductDTO dto = productMapper.getProductDtoWithCategory(product.get());
        return dto;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) {
        Product product = this.productMapper.getProduct(productDTO);
        String savedImagePath = null;
        try {
            savedImagePath = this.fileStorage.uploadFile(image);
            product.setImage(savedImagePath);
            Product productSaved = productRepository.save(product);
            return this.productMapper.getProductDto(productSaved);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedContentTypeException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id",productId)));

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
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id",productId)));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductStatisticsDTO getProductsCount() {
        ProductStatisticsDTO productStatisticsDTO = new ProductStatisticsDTO();
        productStatisticsDTO.setTotalCount(productRepository.count());
        return productStatisticsDTO;
    }

}
