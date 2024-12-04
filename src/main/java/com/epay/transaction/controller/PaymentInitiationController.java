package com.epay.transaction.controller;

import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.PaymentInitiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Class Name:PaymentInitiationController
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
@RequestMapping("/payment/initiation")
public class PaymentInitiationController {

    private final PaymentInitiationService paymentService;

    @PostMapping("/{payMode}")
    public TransactionResponse<PaymentResponse> initiatePayment(@PathVariable("payMode") String payMode, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.initiatePayment(payMode, paymentRequest);
    }
}
