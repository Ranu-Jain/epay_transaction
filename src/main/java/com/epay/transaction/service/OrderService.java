package com.epay.transaction.service;


import com.epay.transaction.dao.OrderDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.OrderRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EPayIdentityUtil;
import com.epay.transaction.util.EncryptionDecryptionUtil;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.validator.OrderValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.encryptdecrypt.service.HashingService;
import com.sbi.epay.encryptdecrypt.util.HashAlgorithm;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;


/**
 * Class Name: OrderService
 * *
 * Description: OrderService class for carrying out order request business logic and encapsulating the application's order functionality.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@RequiredArgsConstructor
public class OrderService {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(OrderService.class);

    private final OrderDao orderDao;
    private final OrderValidator orderValidator;
    private final EncryptionDecryptionUtil encryptionDecryptionUtil;
    private final ObjectMapper objectMapper;
    public TransactionResponse<String> createOrder(OrderRequest orderRequest) {
        EPayPrincipal ePayPrincipal = EPayIdentityUtil.getUserPrincipal();
        //Step 1 : Get Merchant for finding the Keys
        MerchantDto merchantDto = orderDao.getActiveMerchantByMID(ePayPrincipal.getMid()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the orderRequest
        OrderDto orderDto = buildOrderDTO(orderRequest.getOrderRequest(), merchantDto);
        //Step 3 : Validated orderRequest
        orderValidator.validateOrderRequest(orderDto);
        //Step 4 : Generate CustomerId
        orderDto.setHash(encryptionDecryptionUtil.hashValue(orderDto.getMid(), orderDto.getOrderRefNum()));
        orderDto = orderDao.saveOrder(orderDto);
        OrderResponse orderResponse = buildOrderResponse(orderDto);
        return TransactionResponse.<String>builder().data(List.of(EncryptionDecryptionUtil.buildEncryptOrderResponse(orderResponse, merchantDto))).status(1).count(1L).build();
    }

    public TransactionResponse<OrderDto> getOrderById(String orderid) {
        OrderDto orderDto = orderDao.getOrder(orderid);
        logger.info("get order by orderid");
        return TransactionResponse.<OrderDto>builder().data(List.of(orderDto)).status(1).count(1L).build();
    }


    public TransactionResponse<OrderResponse> updateOrderStatus(OrderStatusRequest orderStatusRequest) {
        OrderDto orderDto = orderDao.updateOrder(orderStatusRequest);
        logger.info("update order status");
        return TransactionResponse.<OrderResponse>builder().data(List.of(buildMerchantOrderRequest(orderDto))).status(1).count(1L).build();
    }
    private OrderDto buildOrderDTO(String orderRequest, MerchantDto merchantDto) {
        try {
            String decryptedCustomerRequest = encryptionDecryptionUtil.decryptRequest(orderRequest, merchantDto);
            return objectMapper.readValue(decryptedCustomerRequest, OrderDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Error in buildOrderDTO  ", e);
            throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, orderRequest));
        }
    }
    private String buildOrderData(OrderDto orderDto, MerchantDto merchantDto) {
        try {
            return encryptionDecryptionUtil.encryptRequest(objectMapper.writeValueAsString(orderDto), merchantDto);
        } catch (JsonProcessingException e) {
            logger.error("Error in buildCustomerData  ", e);
            throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, orderDto));
        }
    }


}


