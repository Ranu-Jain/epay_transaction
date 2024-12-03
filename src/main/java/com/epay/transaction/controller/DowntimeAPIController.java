package com.epay.transaction.controller;

import com.epay.transaction.dto.DowntimeAPIDto;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.DowntimeAPIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class Name: DowntimeAPIController
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
@RequestMapping("/downtime/api")
public class DowntimeAPIController {
    private final DowntimeAPIService downtimeAPIService;

    /**
     * Get transaction downtime api details
     *
     * @return list of downtime apis.
     */
    @GetMapping
    @Operation(summary = "Get downtime api details.", description = "Get downtime-api details")
    public TransactionResponse<DowntimeAPIDto> getDowntimeAPIs() {
        return downtimeAPIService.getDowntimeAPIs();
    }
}

