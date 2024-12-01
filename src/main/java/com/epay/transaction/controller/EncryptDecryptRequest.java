package com.epay.transaction.controller;

import com.epay.transaction.dao.OrderDao;
import com.epay.transaction.dto.MerchantDto;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.util.EncryptioDecryptionUtil;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.OrderDetail;
import com.epay.transaction.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;


/**
 * Class Name: EncryptDecryptRequest
 * *
 * Description: create two api for encrypt for encrypt request and decrpt request
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
//THIS CLASS WILL BE DELETE IN PRODUCTION
@RestController
@RequiredArgsConstructor
public class EncryptDecryptRequest {
    private final OrderDao orderDao;
    private final TransactionUtil transactionUtil;


    @PostMapping("/order/encrypt")
    public ResponseEntity<?> encrypt(@RequestBody OrderDetail orderRequest) {
        //Step 1 : Get Merchant for finding the Keys
        MerchantDto merchantDto = orderDao.getActiveMerchantByMID(transactionUtil.getPrincipleUserName()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the cRequest
        String getOrderRequest = EncryptioDecryptionUtil.buildEncryptResponse(orderRequest.getOrderRequest(), merchantDto);
        return new ResponseEntity<>(getOrderRequest, HttpStatus.OK);

    }

    @PostMapping("/order/decrypt")
    public ResponseEntity<?> decrypt(@RequestBody OrderDetail orderRequest) {
        //Step 1 : Get Merchant for finding the Keys
        MerchantDto merchantDto = orderDao.getActiveMerchantByMID(transactionUtil.getPrincipleUserName()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the cRequest
        String getOrderRequest = EncryptioDecryptionUtil.buildDecryptResponse(orderRequest.getOrderRequest(), merchantDto);
        return new ResponseEntity<>(getOrderRequest, HttpStatus.OK);

    }
}
