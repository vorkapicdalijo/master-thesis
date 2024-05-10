package com.fer.hr.payment.controller;

import com.fer.hr.payment.model.CompletedOrder;
import com.fer.hr.payment.model.PaymentOrder;
import com.fer.hr.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/init")
    public PaymentOrder createPayment(
            @RequestParam("sum") BigDecimal sum) {
        return paymentService.createPayment(sum);
    }

    @PostMapping(value = "/capture")
    public CompletedOrder completePayment(@RequestParam("token") String token) {
        return paymentService.completePayment(token);
    }
}
