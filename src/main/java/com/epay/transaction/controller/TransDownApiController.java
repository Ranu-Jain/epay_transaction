package com.epay.transaction.controller;

import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.TransDownApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class Name: TransDownApiController
 * *
 * Description: This class contains apis related to transaction-downtime-api details.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@RestController
@AllArgsConstructor
@RequestMapping("/downtime")
public class TransDownApiController {
    private final TransDownApiService transDownApiService;

    /**
     * Get transaction downtime api details
     *
     * @return list of downtime apis.
     */
    @GetMapping("/api")
    @Operation(summary = "Get Transaction downtime api details.", description = "Get Transaction downtime-api details")
    public TransactionResponse<Object> getDowntimeApi() {
        return transDownApiService.getDowntimeApiDetails();
    }
}

