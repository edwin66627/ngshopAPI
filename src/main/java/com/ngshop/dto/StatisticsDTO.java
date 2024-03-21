package com.ngshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDTO {
    long pendingOrdersTotal;
    long customersTotal;
    long productsTotal;
    long lastWeekTotalSales;
}
