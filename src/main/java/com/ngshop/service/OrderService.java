package com.ngshop.service;

import com.ngshop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> listOrders();
}
