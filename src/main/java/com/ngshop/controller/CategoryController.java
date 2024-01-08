package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.CategoryDTO;
import com.ngshop.entity.HttpResponse;
import com.ngshop.service.CategoryService;
import com.ngshop.utils.ResponseUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    private ResponseEntity<List<CategoryDTO>> listCategories(){
        List<CategoryDTO> categories = categoryService.listCategories();
        return new ResponseEntity<>(categories, OK);
    }

    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(categoryService.getCategory(categoryId), OK);
    }

    @PostMapping("/new")
    private ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), CREATED);
    }

    @PutMapping("/{categoryId}")
    private ResponseEntity<HttpResponse> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId){
        categoryService.updateCategory(categoryDTO,categoryId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "Category"), OK);
    }

    @DeleteMapping("/{categoryId}")
    private ResponseEntity<HttpResponse> updateCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "Category"), OK);
    }

}
