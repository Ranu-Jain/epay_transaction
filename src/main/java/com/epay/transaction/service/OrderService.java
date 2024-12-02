package com.epay.transaction.service;


import com.epay.transaction.dao.OrderDao;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.OrderRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EPayIdentityUtil;
import com.epay.transaction.util.EncryptionDecryptionUtil;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.UniqueIDGenerator;
import com.epay.transaction.validator.OrderValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTimeUtils;
import org.springframework.stereotype.Service;

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
    private final UniqueIDGenerator uniqueIDGenerator;

    public TransactionResponse<OrderResponse> createOrder(OrderRequest orderRequest) {
        //Step 1 : Build orderDTO
        MerchantDto merchantDto = getMerchantDto();
        OrderDto orderDto = buildOrderDTO(orderRequest.getOrderRequest(), merchantDto);
        //Step 2 : Validated orderRequest
        orderValidator.validateOrderRequest(orderDto);
        //Step 3 : Generate Order Hash
        orderDto.setOrderHash(encryptionDecryptionUtil.hashValue(orderDto.getMId(), orderDto.getOrderRefNumber()));
        //Step 4 : Generate SBI Order Reference Number
        orderDto.setSbiOrderRefNumber(uniqueIDGenerator.generateSBIOrderRefNumber());
        //Step 5 : Save Order
        orderDto.setCreatedDate(DateTimeUtils.currentTimeMillis());
        orderDto.setUpdatedDate(DateTimeUtils.currentTimeMillis());
        orderDto = orderDao.saveOrder(orderDto);
        //Step 6 : Build Order Response and send to controller
        return buildTransactionResponse(orderDto, merchantDto);
    }

    public TransactionResponse<OrderResponse> getOrderByOrderRefNumber(String orderRefNumber) {
        MerchantDto merchantDto = getMerchantDto();
        OrderDto orderDto = orderDao.getOrderByOrderRefNumber(orderRefNumber);
        return buildTransactionResponse(orderDto, merchantDto);
    }

    public TransactionResponse<String> updateOrderStatus(String orderRefNumber, String status) {
        orderDao.updateOrderStatus(orderRefNumber, status);
        return TransactionResponse.<String>builder().data(List.of("Order Status updated Successfully.")).status(1).count(1L).build();
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

    private MerchantDto getMerchantDto() {
        EPayPrincipal ePayPrincipal = EPayIdentityUtil.getUserPrincipal();
        return orderDao.getActiveMerchantByMID(ePayPrincipal.getMid()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
    }

    private TransactionResponse<OrderResponse> buildTransactionResponse(OrderDto orderDto, MerchantDto merchantDto) {
        String orderData = buildOrderData(orderDto, merchantDto);
        OrderResponse orderResponse = OrderResponse.builder().orderRefNum(orderDto.getOrderRefNumber()).orderResponse(orderData).build();
        return TransactionResponse.<OrderResponse>builder().data(List.of(orderResponse)).status(1).count(1L).build();
    }
}


