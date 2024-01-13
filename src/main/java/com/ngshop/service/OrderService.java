package com.ngshop.service;

import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.OrderStatusUpdateRequest;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> listOrders();
    void updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest, Long orderId);
    void deleteOrder(Long orderId);
}
