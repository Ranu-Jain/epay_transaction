package com.epay.transaction.controller;

import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.PaymentIntiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Class Name:PaymentIntiationController
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentIntiationController {
   private final PaymentIntiationService paymentService;
    @PostMapping("/initiation/{payMode}/{orderNumber}")
    public TransactionResponse<PaymentResponse> initiatePayment(@PathVariable("payMode") String payMode, @PathVariable("orderNumber") String orderNumber) {
        return paymentService.initiatePayment(payMode, orderNumber);
    }
}
