package com.ngshop.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ngshop.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotEmpty(message = "Name is mandatory")
    private String name;
    @NotEmpty(message = "Description is mandatory")
    private String description;
    private String richDescription;
    private String brand;
    private String image;
    private List<String> images;
    @NotNull(message = "A price is mandatory to ")
    private float price;
    private int countInStock;
    private int rating;
    private int numReviews;
    private boolean isFeatured;
    private Date createdDate;
    private Long categoryId;
    @JsonManagedReference
    private CategoryDTO category;
    
}
