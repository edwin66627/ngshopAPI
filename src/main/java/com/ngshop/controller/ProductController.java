package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.entity.HttpResponse;
import com.ngshop.security.permissions.ProductCreatePermission;
import com.ngshop.security.permissions.ProductDeletePermission;
import com.ngshop.security.permissions.ProductReadPermission;
import com.ngshop.security.permissions.ProductUpdatePermission;
import com.ngshop.service.ProductService;
import com.ngshop.utils.ResponseUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/list")
    public ResponseEntity<Page<ProductDTO>> listProducts(@RequestBody ProductSearchCriteriaDTO productSearchCriteriaDTO){
        return new ResponseEntity<>(productService.listProducts(productSearchCriteriaDTO), OK);
    }

    @ProductReadPermission
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProduct(productId), OK);
    }

    @GetMapping("/{productId}/category")
    public ResponseEntity<ProductDTO> getProductWithCategory(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProductWithCategory(productId), OK);
    }

    @ProductCreatePermission
    @PostMapping("/new")
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") ProductDTO productDTO,
                                                     @RequestPart("images") MultipartFile[] images){
        return new ResponseEntity<>(productService.createProduct(productDTO, images), CREATED);
    }

    @ProductUpdatePermission
    @PutMapping("/{productId}")
    public ResponseEntity<HttpResponse> updateProduct(@Valid @RequestPart("product") ProductDTO productDTO,
                                                       @RequestPart(value = "images", required = false) MultipartFile[] images,
                                                       @PathVariable Long productId){
        productService.updateProduct(productDTO,images, productId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "Product"), OK);
    }

    @ProductDeletePermission
    @DeleteMapping("/{productId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "Product"), OK);
    }

    @ProductReadPermission
    @GetMapping("/count")
    public ResponseEntity<ProductStatisticsDTO> getProductsCount(){
        return new ResponseEntity<>(productService.getProductsCount(), OK);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductStatisticsDTO>> getFeaturedProducts(){
        return null;//new ResponseEntity<>(productService.getProductsCount(), OK);
    }

}
