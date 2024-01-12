package com.ngshop.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private Long productId;
    private float unitPrice;
    private OrderDTO order;
}
