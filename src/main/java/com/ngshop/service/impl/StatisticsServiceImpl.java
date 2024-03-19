package com.ngshop.service.impl;

import com.ngshop.dto.StatisticsDTO;
import com.ngshop.dto.StatisticsRequestDTO;
import com.ngshop.repository.OrderRepository;
import com.ngshop.repository.ProductRepository;
import com.ngshop.repository.UserRepository;
import com.ngshop.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public StatisticsServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StatisticsDTO getDashboardStatistics(StatisticsRequestDTO requestDTO) {
        long pendingOrdersTotal = orderRepository.countByStatus(requestDTO.getStatus());
        long customersTotal = userRepository.countByRolesName(requestDTO.getRole());
        long productsTotal = productRepository.count();
        long lastWeekTotalSales = orderRepository.lastWeekTotalSales();
        StatisticsDTO statisticsDTO = StatisticsDTO.builder().pendingOrdersTotal(pendingOrdersTotal)
                .customersTotal(customersTotal).productsTotal(productsTotal).lastWeekTotalSales(lastWeekTotalSales)
                .build();
        return statisticsDTO;
    }
}
