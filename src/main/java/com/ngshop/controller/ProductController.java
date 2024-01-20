package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.ProductDTO;
import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.dto.ProductStatisticsDTO;
import com.ngshop.entity.HttpResponse;
import com.ngshop.service.ProductService;
import com.ngshop.utils.ResponseUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping("/list")
    private ResponseEntity<List<ProductDTO>> listProducts(@RequestBody ProductSearchCriteriaDTO productSearchCriteriaDTO){
        return new ResponseEntity<>(productService.listProducts(productSearchCriteriaDTO), OK);
    }

    @GetMapping("/{productId}")
    private ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProduct(productId), OK);
    }

    @GetMapping("/{productId}/category")
    private ResponseEntity<ProductDTO> getProductWithCategory(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProductWithCategory(productId), OK);
    }

    @PostMapping("/new")
    private ResponseEntity<ProductDTO> createProduct(@RequestPart("product") ProductDTO productDTO,
                                                     @RequestPart("image") MultipartFile image,
                                                     HttpServletRequest request){
        //String requestUrl = request.getRequestURL().toString();
        return new ResponseEntity<>(productService.createProduct(productDTO, image), CREATED);
    }

    @PutMapping("/{productId}")
    private ResponseEntity<HttpResponse> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        productService.updateProduct(productDTO,productId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "Product"), OK);
    }

    @DeleteMapping("/{productId}")
    private ResponseEntity<HttpResponse> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "Product"), OK);
    }

    @GetMapping("/count")
    private ResponseEntity<ProductStatisticsDTO> getProductsCount(){
        return new ResponseEntity<>(productService.getProductsCount(), OK);
    }

    @GetMapping("/featured")
    private ResponseEntity<List<ProductStatisticsDTO>> getFeaturedProducts(){
        return null;//new ResponseEntity<>(productService.getProductsCount(), OK);
    }

}
