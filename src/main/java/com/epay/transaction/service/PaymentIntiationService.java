package com.epay.transaction.service;

import com.epay.transaction.dao.PaymentTxnDao;
import com.epay.transaction.dao.TokenDao;
import com.epay.transaction.dto.PaymentDto;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.AtrnGeneration;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.UniqueIDGenearator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Class Name:PaymentIntiationService
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Service
@RequiredArgsConstructor
public class PaymentIntiationService {
    private final PaymentTxnDao paymentTxnDao;
    private final TokenService tokenService;
    private final TokenDao tokenDao;
    private final TransactionUtil transactionUtil;

    private final JwtService jwtService;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;

    public TransactionResponse<PaymentResponse> initiatePayment(String token, String paymode, PaymentRequest paymentRequest) {
        String jwtToken = token.replace("Bearer ", "").trim();
        EPayPrincipal epayPrincipal = new EPayPrincipal();
        epayPrincipal.setAuthenticationKey(tokenService.activeTokenid(jwtToken));
        if (!jwtService.isTokenValid(jwtToken, epayPrincipal)) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Token"));
        }

        Claims claims = jwtService.getAllClaimsFromToken(token);
        String merchantId = claims.get("mID", String.class);
        String bookingId = paymentRequest.getOrderReferenceNo();
        if (merchantId == null || bookingId == null) {
            throw new ValidationException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "mid or orderReference Number"));
        }
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAtrn(AtrnGeneration.generateAtrnNumber());
        paymentDto.setAmount(paymentRequest.getAmount());
        paymentDto.setOrderRefNum(paymentRequest.getOrderReferenceNo());
        paymentDto.setCreatedDate(new Date().getTime());
        paymentDto = paymentTxnDao.saveCustomer(paymentDto);
        PaymentResponse paymentResponse = PaymentResponse.builder().atrn(paymentDto.getAtrn()).build();
        return TransactionResponse.<PaymentResponse>builder().data(List.of(paymentResponse)).status(1).count(1L).build();
    }
}

