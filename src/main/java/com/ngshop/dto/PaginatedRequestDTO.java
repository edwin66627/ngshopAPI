package com.ngshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedRequestDTO {
    private int pageSize;
    private int pageNumber;
    private String sortColumn;
    private String sortDirection;
}
