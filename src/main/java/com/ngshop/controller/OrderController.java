package com.ngshop.controller;

import com.ngshop.dto.OrderDTO;
import com.ngshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/new")
    private ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.createOrder(orderDTO), CREATED);
    }

    @GetMapping("/{orderId}")
    private ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId){
        orderService.getOrder(orderId);
        return new ResponseEntity<>(orderService.getOrder(orderId), OK);
    }

}
