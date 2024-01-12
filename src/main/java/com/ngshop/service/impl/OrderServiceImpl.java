package com.ngshop.service.impl;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.dto.OrderDTO;
import com.ngshop.entity.Order;
import com.ngshop.entity.OrderItem;
import com.ngshop.mapper.OrderMapper;
import com.ngshop.repository.OrderRepository;
import com.ngshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.getOrder(orderDTO);
        for(OrderItem orderItem: order.getOrderItems()){
            orderItem.setOrder(order);
        }
        order = orderRepository.save(order);
        return this.orderMapper.getOrderDtoWithNoUserNoAddress(order);
    }

}
