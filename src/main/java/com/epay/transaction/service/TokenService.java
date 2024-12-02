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
import com.sbi.epay.authentication.model.AccessTokenRequest;
import com.sbi.epay.authentication.model.TransactionTokenRequest;
import com.sbi.epay.authentication.service.AuthenticationService;
import com.sbi.epay.authentication.util.enums.TokenType;
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
    private final AuthenticationService authService;

    private static TokenDto buildTokenDTO(String merchantId, TokenType tokenType) {
        return TokenDto.builder().isTokenValid(Boolean.FALSE).merchantId(merchantId).tokenType(tokenType.name()).createdAt(System.currentTimeMillis()).updatedAt(System.currentTimeMillis()).build();
    }

    private static AccessTokenRequest buildAuthRequest(MerchantDto merchantDto) {
        log.info(" Building AuthRequest starts ");
        AccessTokenRequest authRequest = new AccessTokenRequest();
        authRequest.setTokenType(TokenType.ACCESS);
        authRequest.setMid(merchantDto.getMID());
        authRequest.setSecretKey(merchantDto.getSecretKey());
        authRequest.setApiKey(merchantDto.getApiKey());
        authRequest.setExpirationTime(Math.toIntExact(merchantDto.getAccessTokenExpiryTime()));
        log.info(" Building AuthRequest ends ");
        return authRequest;
    }

    private static TransactionTokenRequest buildAuthRequest(OrderDto orderDto, MerchantDto merchantDto) {
        TransactionTokenRequest authRequest = new TransactionTokenRequest();
        authRequest.setTokenType(TokenType.TRANSACTION);
        authRequest.setMid(orderDto.getMid());
        authRequest.setSbiOrderReferenceNumber(orderDto.getSbiOrderRefNum());

        return authRequest;


    }

    public TransactionResponse<String> generateAccessToken(String merchantApiKeyId, String merchantApiKeySecret) {
        log.info("Access Token Request for merchantApiKey {0} merchantApiKeySecret {1}", merchantApiKeyId, merchantApiKeySecret);
        //Step 1 : Get Merchant Object
        MerchantDto merchantDto = tokenDao.getActiveMerchantByKeys(merchantApiKeyId, merchantApiKeySecret).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Token Generation
        log.info(" generating Access token ");
        return generateToken(merchantDto.getMID(), TokenType.ACCESS, buildAuthRequest(merchantDto));
    }

    public TransactionResponse<String> generateTransactionToken(String orderHash) {
        log.info(" Transaction Token Request for orderHash {0} ", orderHash);
        //Step 1 : Get Order Object
        OrderDto orderDto = tokenDao.getActiveTransactionByHashValue(orderHash);
        //Step 2 : Get Merchant Object
        log.info(" request for merchant Info ");
        MerchantDto merchantDto = tokenDao.getActiveMerchantByMID(orderDto.getMid()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        log.info(" getting for merchant Info ");
        //Step 3 : Token Generation
        log.info(" generating transaction token ");
        return generateToken(orderDto.getMid(), TokenType.TRANSACTION, buildAuthRequest(orderDto, merchantDto));
    }

    public TransactionResponse<String> invalidateToken() {
        log.info(" Invalidate Token - Service");
        String mId = extractMidFromToken(jwtToken);
        TokenDto activeTokenDto = tokenDao.getActiveTokenByMID(mId).orElseThrow(() -> new TransactionException(ErrorConstants.EXPIRED_TOKEN_ERROR_CODE, MessageFormat.format(ErrorConstants.EXPIRED_TOKEN_ERROR_MESSAGE, jwtToken)));
        activeTokenDto.setTokenValid(false);
        activeTokenDto.setExpiredAt(System.currentTimeMillis());
        activeTokenDto.setStatus("INACTIVE");
        activeTokenDto.setUpdatedAt(System.currentTimeMillis());
        activeTokenDto.setUpdatedBy(System.getProperty("user.name"));
        tokenDao.saveToken(activeTokenDto, "Invalidate Token");
        //push Notification
        return TransactionResponse.<String>builder().data(List.of("Token invalidated successfully")).status(1).build();
    }

    private TransactionResponse<String> generateToken(String merchantId, TokenType tokenType, AccessTokenRequest accessTokenRequest) {
        //Step 1 : Save Initial Token Request in DB
        log.info(" saving token details initially ");
        TokenDto tokenDto = tokenDao.saveToken(buildTokenDTO(merchantId, tokenType), "Add Token");
        //Step 2 : Generate Token by Authentication Service
        log.info(" Generating token using Utility ");
        String token = authService.generateAccessToken(accessTokenRequest);
        tokenDto.setGeneratedToken(token);
        tokenDto.setTokenValid(Boolean.TRUE);
        tokenDto.setStatus("ACTIVE");
        tokenDto.setExpiredAt(System.currentTimeMillis() * 60 * 1000);
        tokenDto.setCreatedAt(System.currentTimeMillis());
        tokenDto.setCreatedBy(System.getProperty("user.name"));
        tokenDto.setUpdatedBy(System.getProperty("user.name"));
        //Step 3 : Save Token Request in DB with token value
        log.info(" updating token details after generating token ");
        tokenDao.saveToken(tokenDto, "Update Token");
        log.info(" updated token details after generating token ");
        return TransactionResponse.<String>builder().data(List.of(tokenDto.getGeneratedToken())).build();
    }

    private TransactionResponse<String> generateToken(String merchantId, TokenType tokenType, TransactionTokenRequest accessTokenRequest) {
        //Step 1 : Save Initial Token Request in DB
        log.info(" saving token details initially ");
        TokenDto tokenDto = tokenDao.saveToken(buildTokenDTO(merchantId, tokenType), "Add Token");
        //Step 2 : Generate Token by Authentication Service
        log.info(" Generating token using Utility ");
        String token = authService.generateTransactionToken(accessTokenRequest);
        tokenDto.setGeneratedToken(token);
        tokenDto.setTokenValid(Boolean.TRUE);
        tokenDto.setStatus("ACTIVE");
        tokenDto.setExpiredAt(System.currentTimeMillis() * 60 * 1000);
        tokenDto.setCreatedAt(System.currentTimeMillis());
        tokenDto.setCreatedBy(System.getProperty("user.name"));
        tokenDto.setUpdatedBy(System.getProperty("user.name"));
        //Step 3 : Save Token Request in DB with token value
        log.info(" updating token details after generating token ");
        tokenDao.saveToken(tokenDto, "Update Token");
        log.info(" updated token details after generating token ");
        return TransactionResponse.<String>builder().data(List.of(tokenDto.getGeneratedToken())).build();
    }


    public String activeTokenid(String token) {
        log.info("based on token get active id mid ");
        TokenDto jwtToken = tokenDao.getActivetokenbyToken(token);
        return jwtToken.getMerchantId();
    }

}





