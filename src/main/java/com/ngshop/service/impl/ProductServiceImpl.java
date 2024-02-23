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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;
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
    public Page<ProductDTO> listProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO) {
        Page<Product> products = productRepositoryCustom.searchProducts(productSearchCriteriaDTO);
        return products.map(productMapper::getProductDtoWithCategory);
    }

    @Override
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id", productId)));
        return this.productMapper.getProductDto(product);
    }

    @Override
    public ProductDTO getProductWithCategory(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        ProductDTO dto = productMapper.getProductDtoWithCategory(product.get());
        return dto;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile[] images) {
        Product product = this.productMapper.getProduct(productDTO);
        String imagesString;
        try {
            imagesString = this.fileStorage.uploadFile(images);
            product.setImage(imagesString);
            product.setCreatedDate(new Date());
            Product productSaved = productRepository.save(product);
            return this.productMapper.getProductDto(productSaved);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedContentTypeException(e.getMessage());
        }
    }

    @Override
    public void updateProduct(ProductDTO productDTO, @RequestPart("images") MultipartFile[] images, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id", productId)));
        try {
            String imagesString = "";
            if (images != null && images.length > 0) {
                imagesString += product.getImage() + ",";
                imagesString += this.fileStorage.uploadFile(images);
            }

            if (!productDTO.getImagesToDelete().isEmpty() && product.getImage() != null) {
                String[] imagesBeforeUpdate = imagesString.split(",");
                if(!imagesString.isEmpty()) imagesString += ",";
                for (String image : imagesBeforeUpdate) {
                    if (!productDTO.getImagesToDelete().contains(image)) {
                        imagesString += image + ",";
                    }
                }
                imagesString = imagesString.substring(0, imagesString.length() - 1);
                fileStorage.deleteFiles(productDTO.getImagesToDelete());
            }

            if (!imagesString.isEmpty()) {
                product.setImage(imagesString);
            }

            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setBrand(productDTO.getBrand());
            product.setPrice(productDTO.getPrice());
            product.setCountInStock(productDTO.getCountInStock());
            product.setFeatured(productDTO.isFeatured());
            product.setRichDescription(productDTO.getRichDescription());
            product.getCategory().setId(productDTO.getCategory().getId());
            productRepository.save(product);
        } catch (IOException e) {
            List<String> imagesToDelte = productDTO.getImagesToDelete();
            String file = ((NoSuchFileException) e).getFile();
            String message = e.getMessage();
            for(String image: imagesToDelte){
                if(file.contains(image)){
                    message = String.format(ExceptionMessage.NO_SUCH_FILE, image);
                }
            }
            throw new NoSuchElementException(message);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedContentTypeException(e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Product", "id", productId)));

        String[] productImages = product.getImage().split(",");
        try {
            fileStorage.deleteFiles(Arrays.stream(productImages).toList());
            productRepository.deleteById(productId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductStatisticsDTO getProductsCount() {
        ProductStatisticsDTO productStatisticsDTO = new ProductStatisticsDTO();
        productStatisticsDTO.setTotalCount(productRepository.count());
        return productStatisticsDTO;
    }

}
