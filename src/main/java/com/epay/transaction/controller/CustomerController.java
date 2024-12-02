package com.epay.transaction.controller;


import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.CustomerService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Class Name: CustomerController
 * *
 * Description: Customer creation for given Merchant.
 * *
 * Author: V1018400 (Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    LoggerUtility log = LoggerFactoryUtility.getLogger(CustomerController.class);
    private final CustomerService customerService;
    @PostMapping
    public TransactionResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        log.info("Customer Creation is called : customerRequest {}", customerRequest);
        return customerService.saveCustomer(customerRequest);

    }

    @GetMapping("/{customerId}")
    public TransactionResponse<CustomerResponse> getCustomer(@PathVariable("customerId") String customerId) {
        log.info("Customer Get is called : customerId {}", customerId);
        return customerService.getCustomerByCustomerId(customerId);
    }

}
