/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */

package com.epay.transaction.dao;

import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.dto.TokenDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.entity.Token;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repositary.OrderRepository;
import com.epay.transaction.repositary.TokenRepository;
import com.epay.transaction.repositary.cache.MerchantCacheRepo;
import com.epay.transaction.service.TokenService;
import com.epay.transaction.util.ErrorConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenDao {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(TokenService.class);
    private final MerchantCacheRepo merchantCacheRepository;
    private final OrderRepository orderRepository;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final MerchantServicesClient merchantServicesClient;

    public Optional<MerchantDto> getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        log.info(" getActiveMerchantByKeys starts");
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByKeys(merchantApiKey, merchantSecretKey);
        if (merchantDto.isEmpty()) {
            merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantInfo(merchantApiKey, merchantSecretKey));
        }
        log.info(" getActiveMerchantByKeys ends");
        return merchantDto;
    }

    public OrderDto getActiveTransactionByHashValue(String orderHash) {
        log.info(" request for Active Order using Hash from Order DB ");
        Order order = orderRepository.findActiveOrderByHash(orderHash).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Order")));
        log.info(" getting Active Order using Hash from Order DB ");
        return objectMapper.convertValue(order, OrderDto.class);
    }

    public TokenDto saveToken(TokenDto tokenDto) {
        log.info("adding token data in token table");
        Token token = objectMapper.convertValue(tokenDto, Token.class);
        token = tokenRepository.save(token);
        //TODO : Save for Audit Table Also
        return objectMapper.convertValue(token, TokenDto.class);
    }

    public Optional<MerchantDto> getActiveMerchantByMID(String mid) {
        log.info(" request for active merchantId from merchant cache ");
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByMID(mid);
        if (merchantDto.isEmpty()) {
            log.info(" request for active merchantId from merchant API service ");
            merchantDto = Optional.ofNullable(merchantServicesClient.getActiveMerchantByMID(mid));
            log.info(" getting active merchantId from merchant API service ");
        }
        return merchantDto;
    }

    public Optional<TokenDto> getActiveTokenByMID(String mID) {
        log.info(" request for Active Token using MID ");
        Token token = tokenRepository.findActiveTokenByMerchantId(mID);
        log.info(" getting for Active Token using MID ");
        return Optional.ofNullable(objectMapper.convertValue(token, TokenDto.class));
    }

}
