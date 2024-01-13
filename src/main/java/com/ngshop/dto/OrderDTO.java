package com.ngshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ngshop.dto.security.AddressDTO;
import com.ngshop.dto.security.UserDTO;
import com.ngshop.entity.Address;
import com.ngshop.entity.OrderItem;
import com.ngshop.entity.security.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    @NotEmpty(message = "Status is mandatory")
    private String status;
    @Min(value = 0, message = "Total price cannot be less than zero")
    private float totalPrice;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Order date is mandatory")
    private Date orderDate;
    private UserDTO user;
    private Set<OrderItemDTO> orderItems = new HashSet<>();
    private AddressDTO address;
}
