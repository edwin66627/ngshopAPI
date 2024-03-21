package com.ngshop.service;

import com.ngshop.dto.StatisticsDTO;
import com.ngshop.dto.StatisticsRequestDTO;

public interface StatisticsService {
    StatisticsDTO getDashboardStatistics(StatisticsRequestDTO requestDTO);
}
