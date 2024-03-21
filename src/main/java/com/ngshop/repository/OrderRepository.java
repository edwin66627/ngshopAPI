package com.ngshop.repository;

import com.ngshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    long countByStatus(String status);
    @Query("SELECT SUM(o.totalPrice) FROM Order AS o")
    long totalSales();
    @Query(value = "SELECT SUM(o.total_price) FROM orders AS o WHERE order_date BETWEEN date_sub(now(), INTERVAL 1 WEEK) and now()",
        nativeQuery = true)
    long lastWeekTotalSales();
}
