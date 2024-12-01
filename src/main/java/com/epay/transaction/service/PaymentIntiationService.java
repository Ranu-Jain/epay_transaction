package com.epay.transaction.service;

import com.epay.transaction.dao.PaymentTxnDao;
import com.epay.transaction.dao.TokenDao;
import com.epay.transaction.dto.PaymentDto;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.AtrnGeneration;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.UniqueIDGenearator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Class Name:PaymentIntiationService
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
@Service
@RequiredArgsConstructor
public class PaymentIntiationService {
    private final PaymentTxnDao paymentTxnDao;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;

   package com.example.payment.service;

import com.example.payment.dto.PaymentResponse;
import com.example.payment.dto.TransactionResponse;
import com.example.payment.entity.Transaction;
import com.example.payment.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;

    public PaymentService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse<PaymentResponse> initiatePayment(String payMode, String orderNumber) {
        // Step 1: Fetch Merchant Details
        EPayPrincipal ePayPrincipal = (EPayPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String merchantId = ePayPrincipal.getMerchantId();

        // Step 2: Fetch Order Details
        // Assuming order details are fetched from a repository or API call.
        // Example Placeholder
        Optional<Transaction> existingOrder = transactionRepository.findByOrderId(orderNumber);
        if (existingOrder.isEmpty()) {
            return TransactionResponse.<PaymentResponse>builder()
                    .status(0)
                    .data(List.of())
                    .count(0L)
                    .build();
        }

        Transaction order = existingOrder.get();

        // Step 3: Ensure Transaction Without ATRN
        if (order.getAtrn() != null) {
            throw new IllegalStateException("ATRN already exists. Cannot initiate payment again.");
        }

        // Step 4: Fetch Transaction Charges Details
        // Assume service to get transaction charges based on merchant config
        double transactionCharges = getTransactionCharges(merchantId, order.getOrderAmount(), payMode);

        // Step 5: Validate
        validatePayment(merchantId, order, payMode);

        // Step 6: Generate ATRN Number
        String atrn = UUID.randomUUID().toString();
        order.setAtrn(atrn);
        order.setStatus("PENDING");
        transactionRepository.save(order);

        // Step 7: Call Payment Service API
        String paymentUrl = callPaymentGatewayAPI(payMode, atrn, order);

        // Step 8: Construct Response
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .atrn(atrn)
                .paymentUrl(paymentUrl)
                .status("PENDING")
                .build();

        return TransactionResponse.<PaymentResponse>builder()
                .status(1)
                .count(1L)
                .data(List.of(paymentResponse))
                .build();
    }

    private double getTransactionCharges(String merchantId, double orderAmount, String payMode) {
        // Simulate fetching configured charges
        return orderAmount * 0.02; // Example: 2% transaction charge
    }

    private void validatePayment(String merchantId, Transaction order, String payMode) {
        if (!"OPEN".equals(order.getStatus())) {
            throw new IllegalStateException("Order is not in a valid state for payment initiation.");
        }
        // Add additional validations for limits, payMode, etc.
    }

    private String callPaymentGatewayAPI(String payMode, String atrn, Transaction order) {
        // Placeholder for Payment Gateway Integration
        // Example: Return a dummy URL
        return "https://paymentgateway.example.com/pay?atrn=" + atrn;
    }
}

