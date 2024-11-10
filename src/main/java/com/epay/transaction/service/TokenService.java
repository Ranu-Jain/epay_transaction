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
        MerchantDto merchantDto = tokenDao.getActiveMerchantByKeys(merchantApiKey, merchantSecretKey).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        TokenDto tokenDto = TokenDto.builder().
                isTokenValid(Boolean.FALSE).
                merchantId(merchantDto.getMID()).
                tokenType(TokenType.CUSTOMER.name()).
                build();
        tokenDto = tokenDao.saveToken(tokenDto);

        //TODO : Build AuthRequest
        AuthRequest authRequest = new AuthRequest();
        authRequest.setApiKey(merchantApiKey);
        authRequest.setSecretKey(merchantApiKey);
        authRequest.setTokenType(TokenType.CUSTOMER);
        authRequest.setMid(merchantDto.getMID());
        authRequest.setExpirationTime(merchantDto.getAccessTokenExpiryTime());

        //TODO : call AuthService for token generation
        String token = authService.generateToken(authRequest);
        //TODO : Build Token DTO
        tokenDto.setGeneratedToken(token);
        tokenDto.setTokenValid(Boolean.TRUE);
        tokenDao.updateToken(tokenDto);
        return TransactionResponse.<String>builder().data(List.of(tokenDto.getGeneratedToken())).build();
    }


    public TransactionResponse<String> generateTransactionToken(String orderHash) {
        log.info("service-Transaction token ");
        //TODO: call OrderDB to get OrderDetails
        OrderDto orderDto = tokenDao.getActiveTransactionByHashValue(orderHash);

        TokenDto tokenDto = TokenDto.builder().isTokenValid(false).build();
        tokenDto.setMerchantId(orderDto.getMID());
        tokenDto.setTokenType(TokenType.TRANSACTION.name());
        tokenDto = tokenDao.saveToken(tokenDto);

        //TODO : Build AuthRequest
        AuthRequest authRequest = new AuthRequest();
        authRequest.setHashValue(orderHash);
        authRequest.setTokenType(TokenType.TRANSACTION);
        authRequest.setMid(orderDto.getMID());
        authRequest.setSbiOrderReference(orderDto.getOrderRefNum());
        authRequest.setExpirationTime(orderDto.getExpiry());

        //TODO :call AuthService for token generation
        String token = authService.generateToken(authRequest);

        //TODO :Build Token DTO
        tokenDto.setGeneratedToken(token);
        tokenDto.setTokenValid(Boolean.TRUE);
        tokenDao.updateToken(tokenDto);
        return TransactionResponse.<String>builder().data(List.of(tokenDto.getGeneratedToken())).build();
    }

}
