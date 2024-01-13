package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.OrderStatusUpdateRequest;
import com.ngshop.entity.Order;
import com.ngshop.entity.OrderItem;
import com.ngshop.mapper.OrderMapper;
import com.ngshop.repository.OrderRepository;
import com.ngshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDTO> listOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Order", orderId)));
        order.setStatus(orderStatusUpdateRequest.getStatus());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.getOrder(orderDTO);
        for(OrderItem orderItem: order.getOrderItems()){
            orderItem.setOrder(order);
        }
        order = orderRepository.save(order);
        return this.orderMapper.getOrderDtoWithNoUserNoAddress(order);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Order", orderId)));

        return orderMapper.getOrderDto(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException(String.format(ExceptionMessage.NO_SUCH_ELEMENT, "Order", orderId)));

        orderRepository.deleteById(orderId);
    }

}
