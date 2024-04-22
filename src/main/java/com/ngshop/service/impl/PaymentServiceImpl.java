package com.ngshop.service.impl;

import com.ngshop.dto.OrderDTO;
import com.ngshop.dto.OrderItemDTO;
import com.ngshop.dto.PaymentResponseDTO;
import com.ngshop.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Value("${stripe.success.url}")
    private String stripeSuccessUrl;
    @Value("${stripe.cancel.url}")
    private String stripeCancelUrl;

    @Override
    public PaymentResponseDTO createPaymentSession(OrderDTO order){
        log.info(order.toString());
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            // If you are trying to reference an existing customer be sure to create the Customer before setting this field.
            // Otherwise, if you do not intend to reference a customer you can omit the field altogether.
            //.setCustomer(order.getUser().getId().toString())
            .setSuccessUrl(stripeSuccessUrl)
            .setCancelUrl(stripeCancelUrl);

        for(OrderItemDTO orderItem: order.getOrderItems()){
            paramsBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    //.setPrice(orderItem.getUnitPrice())
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .putMetadata("app_id", orderItem.getProduct().getId().toString())
                                    .setName(orderItem.getProduct().getName())
                                .build()
                            )
                            .setCurrency("usd")
                            .setUnitAmountDecimal(BigDecimal.valueOf(orderItem.getUnitPrice() * 100)) // amount in cents
                            .build())
                .build());
        }

        Session session = null;
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        try {
            session = Session.create(paramsBuilder.build());
            paymentResponseDTO.setSessionId(session.getId());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return paymentResponseDTO;
    }
}
