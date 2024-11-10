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
import com.epay.transaction.dto.TokenDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.entity.Token;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repositary.OrderRepository;
import com.epay.transaction.repositary.TokenRepository;
import com.epay.transaction.repositary.cache.MerchantCacheRepository;
import com.epay.transaction.util.ErrorConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenDao {

    private final MerchantCacheRepository merchantCacheRepository;
    private final OrderRepository orderRepository;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final MerchantServicesClient merchantServicesClient;

    public Optional<MerchantDto> getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByKeys(merchantApiKey, merchantSecretKey);
        if(merchantDto.isEmpty()){
             merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantInfo(merchantApiKey, merchantSecretKey));
        }
        return merchantDto;
    }

    public OrderDto getActiveTransactionByHashValue(String orderHash) {
        Order order = orderRepository.findActiveOrderByHash(orderHash).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Order")));
        return objectMapper.convertValue(order, OrderDto.class);
    }

    public TokenDto saveToken(TokenDto tokenDto) {
        Token token = objectMapper.convertValue(tokenDto, Token.class);
        token = tokenRepository.save(token);
        //TODO : Save for Audit Table Also
        return objectMapper.convertValue(token, TokenDto.class);
    }
}
