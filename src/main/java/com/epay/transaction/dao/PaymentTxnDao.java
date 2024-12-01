package com.epay.transaction.dao;

import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.PaymentDto;
import com.epay.transaction.entity.Customer;
import com.epay.transaction.entity.PaymentTransaction;
import com.epay.transaction.repositary.PaymentIntiationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class Name:PaymentTxnDao
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
@Component
@RequiredArgsConstructor

public class PaymentTxnDao {
    private final ObjectMapper objectMapper;
    private final PaymentIntiationRepo paymentIntiationRepo;
    public PaymentDto saveCustomer(PaymentDto paymentDto) {
        PaymentTransaction paymentTransaction = objectMapper.convertValue(paymentDto, PaymentTransaction.class);
        paymentTransaction = paymentIntiationRepo.save(paymentTransaction);
        return objectMapper.convertValue(paymentTransaction, PaymentDto.class);
    }
}
