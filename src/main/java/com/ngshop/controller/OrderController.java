package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.OrderStatusUpdateRequest;
import com.ngshop.entity.HttpResponse;
import com.ngshop.service.OrderService;
import com.ngshop.utils.ResponseUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    private ResponseEntity<List<OrderDTO>> listOrders(){
        return new ResponseEntity<>(orderService.listOrders(), OK);
    }

    @PostMapping("/new")
    private ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.createOrder(orderDTO), CREATED);
    }

    @GetMapping("/{orderId}")
    private ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.getOrder(orderId), OK);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId){
        return new ResponseEntity<>(orderService.getUserOrders(userId), OK);
    }

    @PutMapping("/{orderId}")
    private ResponseEntity<HttpResponse> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest,
                                                           @PathVariable Long orderId){
        orderService.updateOrderStatus(orderStatusUpdateRequest, orderId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "Order status"), OK);
    }

    @DeleteMapping("/{orderId}")
    private ResponseEntity<HttpResponse> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "Order"), OK);
    }

}
