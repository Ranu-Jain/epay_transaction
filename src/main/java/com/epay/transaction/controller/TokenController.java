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

import com.epay.transaction.service.TokenService;
import com.epay.transaction.model.response.TransactionResponse;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(TokenController.class);

    private final TokenService tokenService;

    @PostMapping("/access")
    @Operation(summary = "Access Token Generation")
    public TransactionResponse<String> generateAccessToken(@RequestHeader("Merchant-API-Key") String merchantApiKey, @RequestHeader("Merchant-Secret-Key") String merchantSecretKey) {
        log.info(" Token API Controller access token Start ");
       return tokenService.generateAccessToken(merchantApiKey, merchantSecretKey);
    }

    @PostMapping("/transaction/{orderHash}")
    @Operation(summary = "Transaction Token Generation")
    public TransactionResponse<String> generateTransactionToken(@PathVariable("orderHash") String orderHash) {
        log.info(" Token API Controller transaction token Start ");
        return tokenService.generateTransactionToken(orderHash);
    }

}
