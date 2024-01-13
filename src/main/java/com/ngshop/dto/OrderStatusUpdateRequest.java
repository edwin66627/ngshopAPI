package com.ngshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    @NotEmpty(message = "Status is mandatory")
    private String status;
}
