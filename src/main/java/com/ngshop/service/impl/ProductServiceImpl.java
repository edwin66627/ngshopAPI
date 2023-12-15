package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.entity.Product;
import com.ngshop.repository.ProductRepository;
import com.ngshop.repository.ProductRepositoryCustom;
import com.ngshop.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductRepositoryCustom productRepositoryCustom;
    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> listProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO) {
        List<Product> products = productRepositoryCustom.searchProducts(productSearchCriteriaDTO);
        return products.stream().map(this::convertToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", productId)));
        return convertToProductDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        Product productSaved = productRepository.save(product);
        return convertToProductDTO(productSaved);
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

    @Override
    public List<ProductDTO> listFeaturedProducts() {
        return null;
    }

    private ProductDTO convertToProductDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToProduct(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

}
