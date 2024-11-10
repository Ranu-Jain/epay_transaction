/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */
package com.epay.transaction.service;

import com.epay.transaction.dao.TokenDao;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.dto.TokenDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.ErrorConstants;
import com.sbi.epay.authentication.enumeration.TokenType;
import com.sbi.epay.authentication.model.AuthRequest;
import com.sbi.epay.authentication.service.AuthService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TokenService {


    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(TokenService.class);
    private final TokenDao tokenDao;
    private final AuthService authService;

    public TransactionResponse<String> generateAccessToken(String merchantApiKey, String merchantSecretKey) {
        log.info("Access Token Request for merchantApiKey {0} merchantSecretKey {1}", merchantApiKey, merchantSecretKey);
        //Step 1 : Get Merchant Object
        MerchantDto merchantDto = tokenDao.getActiveMerchantByKeys(merchantApiKey, merchantSecretKey).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Token Generation
        return generateToken(merchantDto.getMID(), TokenType.CUSTOMER, buildAuthRequest(merchantDto));
    }
    public TransactionResponse<String> generateTransactionToken(String orderHash) {
        log.info("Transaction Token Request for orderHash {0} ", orderHash);
        //Step 1 : Get Order Object
        OrderDto orderDto = tokenDao.getActiveTransactionByHashValue(orderHash);
        //Step 2 : Token Generation
        return generateToken(orderDto.getMID(), TokenType.TRANSACTION, buildAuthRequest(orderDto));
    }

    private TransactionResponse<String> generateToken(String merchantId, TokenType customer, AuthRequest authRequest) {
        //Step 1 : Save Initial Token Request in DB
        TokenDto tokenDto = tokenDao.saveToken(buildTokenDTO(merchantId, customer));
        //Step 2 : Generate Token by Authentication Service
        String token = authService.generateToken(authRequest);
        tokenDto.setGeneratedToken(token);
        tokenDto.setTokenValid(Boolean.TRUE);
        //Step 3 : Save Token Request in DB with token value
        tokenDao.saveToken(tokenDto);
        return TransactionResponse.<String>builder().data(List.of(tokenDto.getGeneratedToken())).build();
    }

    private static TokenDto buildTokenDTO(String merchantId, TokenType tokenType) {
        return TokenDto.builder().isTokenValid(Boolean.FALSE).merchantId(merchantId).tokenType(tokenType.name()).createdAt(System.currentTimeMillis()).updatedAt(System.currentTimeMillis()).build();
    }

    private static AuthRequest buildAuthRequest(MerchantDto merchantDto) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setTokenType(TokenType.CUSTOMER);
        authRequest.setMid(merchantDto.getMID());
        authRequest.setExpirationTime(merchantDto.getAccessTokenExpiryTime());
        return authRequest;
    }

    private static AuthRequest buildAuthRequest(OrderDto orderDto) {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setTokenType(TokenType.TRANSACTION);
        authRequest.setMid(orderDto.getMID());
        authRequest.setSbiOrderReference(orderDto.getOrderRefNum());
        authRequest.setExpirationTime(orderDto.getExpiry());
        return authRequest;
    }

}
