package com.epay.transaction.controller;


import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return customerService.saveCustomer(customerRequest);
    }

}






