package com.ngshop.controller;

import com.ngshop.dto.StatisticsDTO;
import com.ngshop.dto.StatisticsRequestDTO;
import com.ngshop.security.permissions.StatisticsReadPermission;
import com.ngshop.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @StatisticsReadPermission
    @PostMapping("/")
    public ResponseEntity<StatisticsDTO> getDashboardStatistics(@RequestBody StatisticsRequestDTO requestDTO) {
        return new ResponseEntity<>(statisticsService.getDashboardStatistics(requestDTO), OK);
    }


}
