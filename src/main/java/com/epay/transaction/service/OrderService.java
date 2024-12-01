package com.epay.transaction.service;



import com.epay.transaction.dao.OrderDao;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.Order;
import com.epay.transaction.entity.OrderStatus;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.model.request.OrderRequest;
import com.epay.transaction.model.request.OrderStatusRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.*;
import com.epay.transaction.validator.OrderValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.JwtService;
import com.sbi.epay.encryptdecrypt.service.HashingService;
import com.sbi.epay.encryptdecrypt.util.HashAlgorithm;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.util.Date;
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
    private final TransactionalComponent transactionalComponent;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final OrderDao orderDao;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final OrderValidator orderValidator;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(OrderService.class);

    /**
     * create order
     *
     * @param orderRequestDtl as Object
     * @param token as String
     * @return Object of MerchantOrderResponse.
     */

    public TransactionResponse<String> createOrder(OrderDetail orderRequestDtl, String token) {

            String jwtToken = token.replace("Bearer ", "").trim();
            EPayPrincipal epayPrincipal = new EPayPrincipal();
            epayPrincipal.setAuthenticationKey(tokenService.activeTokenid(jwtToken));
            if (!jwtService.isTokenValid(jwtToken, epayPrincipal)) {
                throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Token"));
            }
            //Step 1 : Get Merchant for finding the Keys
            MerchantDto merchantDto = orderDao.getActiveMerchantByMID(jwtService.getUsernameFromToken(jwtToken)).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
            //Step 2 : Decrypt the orderRequest
            String getOrderRequest = EncryptioDecryptionUtil.buildDecryptResponse(orderRequestDtl.getOrderRequest(), merchantDto);
            OrderRequest orderRequest = buildOrderRequestOrder(getOrderRequest);
            OrderDto order = buildOrderDto(orderRequest);
            orderValidator.validateOrderRequest(order);
            String orderidMid=order.getId()+order.getMid();
        byte[] hash_SHA512=HashingService.generateHash(orderidMid.getBytes(StandardCharsets.UTF_8), HashAlgorithm.valueOf(HashAlgorithm.SHA_512.toString()));
        logger.info("hashvalue1:"+hash_SHA512);
          order.setHash(AggBase64CoderAndStandardHashing.byteToStringHash(hash_SHA512));
            order.setToken(jwtToken);
            OrderDto orderDto = orderDao.saveOrder(order);
            OrderResponse orderResponse = buildOrderResponse(orderDto);
        return TransactionResponse.<String>builder().data(List.of(EncryptioDecryptionUtil.buildEncryptOrderResponse(orderResponse,merchantDto))).status(1).count(1L).build();
    }


    /**
     * get order detail by orderid
     *
     * @param orderid as String
     * @return Object of MerchantOrder.
     */
    public TransactionResponse<OrderDto> getOrderById(String orderid) {
        OrderDto orderDto = orderDao.getOrder(orderid);
        logger.info("get order by orderid");
        return TransactionResponse.<OrderDto>builder().data(List.of(orderDto)).status(1).count(1L).build();
    }


    /**
     * Get all order's detail based on pagenumber and pagesize
     *
     * @param pageSize as int
     * @param pagenumber as int
     * @return Page<?> of MerchantOrder.
     */

    public TransactionResponse<Page<Order>> getOrderByPages(String mid,int pageSize, int pagenumber) {

        Page<Order> pages = orderDao.getOrderByPages(mid, pageSize, pagenumber);
        logger.info("get all order by pagination");
        return TransactionResponse.<Page<Order>>builder().data(List.of(pages)).status(1).count(1L).build();

    }


    /**
     * update order detail status
     *
     * @param orderStatusRequest as Object
     * @return Object of MerchantOrderResponse.
     */
    public TransactionResponse<OrderResponse> updateOrderStatus(OrderStatusRequest orderStatusRequest) {
        OrderDto orderDto = orderDao.updateOrder(orderStatusRequest);
        logger.info("update order status");
        return TransactionResponse.<OrderResponse>builder().data(List.of(buildMerchantOrderRequest(orderDto))).status(1).count(1L).build();
    }


    /**
     * Validate orderid and return boolean value of status
     *
     * @param orderid as String
     * @return boolean of validate status.
     */
    public TransactionResponse<Boolean> validateOrderid(String orderid) {
        logger.info("Validate orderid");
        return TransactionResponse.<Boolean>builder().data(List.of(orderDao.getOrderStatus(orderid))).status(1).count(1L).build();
    }


    /**
     * create order response by using orderDto
     *
     * @param orderDto as Object
     * @return Object of MerchantOrderResponse.
     */

    public OrderResponse buildMerchantOrderRequest(OrderDto orderDto) {
        return OrderResponse.builder().orderId(orderDto.getId())
                .status(OrderStatus.valueOf(orderDto.getStatus()))
                .mId(orderDto.getMid())
                .amount(orderDto.getAmount())
                .customerId(orderDto.getMerchantCustomerId())
                .currency(orderDto.getCurrencyId())
                .merchantOrderNumber(orderDto.getId())
                .bankOrderNumber("")
                .paymentMode(orderDto.getPaymentMode())
                .successFailURL(orderDto.getCallbackUrl())
                .otherDetails(orderDto.getOtherDetails())
                .multiAccount("")
                .gatewayMapID(orderDto.getOperationMode())
                .paymentURL(orderDto.getCallbackUrl())
                .attempts(0)
                .createdAt(new Date().getTime()).build();
    }

    /**
     * create  order request as input String orderRequest
     *
     * @param orderRequest as string
     * @return Object of MerchantOrderRequest.
     */

    public static OrderRequest buildOrderRequestOrder(String orderRequest) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(orderRequest, OrderRequest.class);
        } catch (JsonProcessingException e) {
            throw new TransactionException(ErrorConstants.INVALID_JSON_FORMAT_CODE, ErrorConstants.INVALID_JSON_FORMAT_MESSAGE);
        }
    }

    /**
     * create OrderDto by using order request
     *
     * @param orderRequest as Object
     * @return Object of OrderDto.
     */

    public OrderDto buildOrderDto(OrderRequest orderRequest) {
        return OrderDto.builder()
        .id(orderRequest.getMid() + uniqueIDGenearator.generateOrderRequestId())
        .mid(orderRequest.getMid())
        .status(orderRequest.getStatus())
        .amount(orderRequest.getAmount())
        .merchantCustomerId(orderRequest.getCustomerId())
        .currencyId(orderRequest.getCurrency())
        .operationMode(orderRequest.getPaymentMode())
        .paymentMode(orderRequest.getPaymentMode())
        .callbackUrl(orderRequest.getSuccessFailURL())
        .otherDetails(orderRequest.getOtherDetails())
        .orderGenerationMode(orderRequest.getPaymentMode()).build();
    }

    /**
     * create order response by using orderDto
     *
     * @param orderDto as Object
     * @return Object of OrderDto.
     */
    public OrderResponse buildOrderResponse(OrderDto orderDto) {
        return OrderResponse.builder().orderId(orderDto.getId())
                .status(OrderStatus.valueOf(orderDto.getStatus()))
                .mId(orderDto.getMid())
                .amount(orderDto.getAmount())
                .customerId(orderDto.getMerchantCustomerId())
                .currency(orderDto.getCurrencyId())
                .merchantOrderNumber(orderDto.getId())
                .bankOrderNumber("")
                .paymentMode(orderDto.getPaymentMode())
                .successFailURL(orderDto.getCallbackUrl())
                .otherDetails(orderDto.getOtherDetails())
                .multiAccount("")
                .gatewayMapID(orderDto.getOperationMode())
                .paymentURL(transactionalComponent.getPaymentUrl() + orderDto.getHash())
                .attempts(0)
                .createdAt(new Date().getTime()).build();
    }
}


