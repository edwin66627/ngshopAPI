package com.ngshop.controller;

import com.ngshop.constant.ResponseMessage;
import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.OrderStatusUpdateRequest;
import com.ngshop.dto.PaymentResponseDTO;
import com.ngshop.entity.HttpResponse;
import com.ngshop.security.permissions.OrderCreatePermission;
import com.ngshop.security.permissions.OrderDeletePermission;
import com.ngshop.security.permissions.OrderReadPermission;
import com.ngshop.security.permissions.OrderUpdatePermission;
import com.ngshop.service.OrderService;
import com.ngshop.service.PaymentService;
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
    private final PaymentService paymentService;
    @Autowired
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @OrderReadPermission
    @GetMapping("/list")
    private ResponseEntity<List<OrderDTO>> listOrders(){
        return new ResponseEntity<>(orderService.listOrders(), OK);
    }

    @OrderCreatePermission
    @PostMapping("/new")
    private ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderService.createOrder(orderDTO), CREATED);
    }

    @OrderReadPermission
    @GetMapping("/{orderId}")
    private ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.getOrder(orderId), OK);
    }

    @OrderReadPermission
    @GetMapping("/user/{userId}")
    private ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId){
        return new ResponseEntity<>(orderService.getUserOrders(userId), OK);
    }

    @OrderUpdatePermission
    @PutMapping("/{orderId}")
    private ResponseEntity<HttpResponse> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest,
                                                           @PathVariable Long orderId){
        orderService.updateOrderStatus(orderStatusUpdateRequest, orderId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.UPDATE_SUCCESS, "Order status"), OK);
    }

    @OrderDeletePermission
    @DeleteMapping("/{orderId}")
    private ResponseEntity<HttpResponse> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseUtility.buildResponse(String.format(ResponseMessage.DELETE_SUCCESS, "Order"), OK);
    }

    @OrderCreatePermission
    @PostMapping("/create-checkout-session")
    private ResponseEntity<PaymentResponseDTO> createPaymentSession(@RequestBody OrderDTO order){
        return new ResponseEntity<>(paymentService.createPaymentSession(order), OK);
    }

}
