package com.ngshop.service;


import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPaymentSession(OrderDTO order);
}
