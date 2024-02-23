package com.ngshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteriaDTO {
    private int pageSize;
    private int pageNumber;
    private String sortColumn;
    private String sortDirection;
    @JsonProperty
    private boolean isFeatured;
    private List<Long> categories;


}
