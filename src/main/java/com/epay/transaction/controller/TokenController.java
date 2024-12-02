/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */

package com.epay.transaction.controller;

import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.service.TokenService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final LoggerUtility log = LoggerFactoryUtility.getLogger(this.getClass());
    private final TokenService tokenService;

    @PostMapping("/access")
    @Operation(summary = "Access Token Generation")
    public TransactionResponse<String> generateAccessToken(@RequestHeader("Merchant-API-Key-Id") String merchantApiKeyId, @RequestHeader("Merchant-API-Key-Secret") String merchantApiKeySecret) {
        log.info("Access Token Request,  merchantApiKeyId : {}, merchantApiKeySecret : {}", merchantApiKeyId, merchantApiKeySecret);
        return tokenService.generateAccessToken(merchantApiKeyId, merchantApiKeySecret);
    }

    @PostMapping("/transaction/{orderHash}")
    @Operation(summary = "Transaction Token Generation")
    public TransactionResponse<String> generateTransactionToken(@PathVariable("orderHash") String orderHash) {
        log.info("Transaction Token Request,  orderHash : {}", orderHash);
        return tokenService.generateTransactionToken(orderHash);
    }

    @PostMapping("/invalidate")
    @Operation(summary = "Transaction Invalidation")
    public TransactionResponse<String> invalidateToken() {
        return tokenService.invalidateToken();
    }
}
