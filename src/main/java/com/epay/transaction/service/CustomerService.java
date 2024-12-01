package com.epay.transaction.service;

/*
  Class Name:CustomerService
  *
  Description:
  *
  Author:V1014352(Ranjan Kumar)
  <p>
  Copyright (c) 2024 [State Bank of India]
  All right reserved
  *
  Version:1.0
 */

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dao.TokenDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.TokenDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EncryptioDecryptionUtil;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.UniqueIDGenearator;
import com.epay.transaction.validator.CustomerValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.JwtService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;
    private  final TokenService tokenService;
    private  final TokenDao tokenDao;
    private final TransactionUtil transactionUtil;
    
    private final JwtService jwtService;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;
    private final CustomerValidator customerValidator;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(CustomerService.class);

    public TransactionResponse<CustomerResponse> saveCustomer(CustomerRequest customerRequest, String token) {
        String jwtToken = token.replace("Bearer ", "").trim();
        EPayPrincipal epayPrincipal = new EPayPrincipal();
        epayPrincipal.setAuthenticationKey(tokenService.activeTokenid(jwtToken));
        if (!jwtService.isTokenValid(jwtToken, epayPrincipal)) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Token"));
        }

        //Step 1 : Get Merchant for finding the Keys
        logger.info("get Merchant Id");
        MerchantDto merchantDto = customerDao.getActiveMerchantByMID(jwtService.getUsernameFromToken(jwtToken)).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the customerRequest
        logger.info("create Customer Dto");
        CustomerDto customerDto = EncryptioDecryptionUtil.buildCustomerDTO(customerRequest, merchantDto);
        //Step 3 : Validated customerRequest

        customerValidator.validateCustomerRequest(customerDto);
        //Step 4 : Generate CustomerId
        customerDto.setCustomerId(uniqueIDGenearator.generateUniqueCustomerId());
        customerDto.setCreatedDate(new Date().getTime());
        customerDto.setUpdatedDate(new Date().getTime());
        customerDto = customerDao.saveCustomer(customerDto);
        //Step 6 : Build Customer Response and send to controller
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customerDto.getCustomerId()).customerResponse(EncryptioDecryptionUtil.buildEncryptCustomerResponse(customerDto, merchantDto)).build();
        return TransactionResponse.<CustomerResponse>builder().data(List.of(customerResponse)).status(1).count(1L).build();
    }

    public TransactionResponse<CustomerResponse> getCustomerByCustomerId(String customerId) {
        CustomerDto customerDto = customerDao.getCustomerByCustomerId(customerId).orElseThrow(() ->
                new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Customer")));
        MerchantDto merchantDto = customerDao.getActiveMerchantByMID(customerDto.getMId()).orElseThrow(() ->
                new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customerDto.getCustomerId()).
                customerResponse(EncryptioDecryptionUtil.buildEncryptCustomerResponse(customerDto, merchantDto)).build();
        return TransactionResponse.<CustomerResponse>builder().data(List.of(customerResponse)).status(1).count(1L).build();
    }



}

