package com.ngshop.controller;

import com.ngshop.dto.ProductDTO;
import com.ngshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<List<ProductDTO>> listProducts(){
        return new ResponseEntity<>(productService.listProducts(), OK);
    }

    @GetMapping("/{productId}")
    private ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getProduct(productId), OK);
    }

    @PostMapping("/new")
    private ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.createProduct(productDTO), CREATED);
    }
}
