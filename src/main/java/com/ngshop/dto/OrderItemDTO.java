package com.ngshop.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private float unitPrice;
    private OrderDTO order;
    private ProductDTO product;
}
