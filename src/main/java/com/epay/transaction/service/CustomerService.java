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
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionUtil;
import com.epay.transaction.util.UniqueIDGenearator;
import com.epay.transaction.validator.CustomerValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.util.util.EncryptionDecryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final TransactionUtil transactionUtil;
    private final EncryptionDecryptionUtil encryptionDecryptionUtil;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;
    private final CustomerValidator customerValidator;

    public TransactionResponse<CustomerResponse> saveCustomer(CustomerRequest customerRequest) {
        //Step 1 : Get Merchant for finding the Keys
        MerchantDto merchantDto = customerDao.getActiveMerchantByMID(transactionUtil.getPrincipleUserName()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the customerRequest
        CustomerDto customerDto = buildCustomerDTO(customerRequest, merchantDto);
        //Step 3 : Validated customerRequest
        customerValidator.validateCustomerRequest(customerDto);
        //Step 4 : Generate CustomerId
        customerDto.setMerchantId(merchantDto.getMID());
        customerDto.setCustomerId(uniqueIDGenearator.generateUniqueCustomerId());
        //Step 5 : Save Customer Data
        customerDto = customerDao.saveCustomer(customerDto);
        //Step 6 : Build Customer Response and send to controller
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customerDto.getCustomerId()).customerResponse(buildEncryptCustomerResponse(customerDto, merchantDto)).build();
        return TransactionResponse.<CustomerResponse>builder().data(List.of(customerResponse)).status(1).count(1L).build();
    }

    public TransactionResponse<CustomerResponse> getCustomerByCustomerId(String customerId) {
        CustomerDto customerDto = customerDao.getCustomerByCustomerId(customerId).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Customer")));
        MerchantDto merchantDto = customerDao.getActiveMerchantByMID(customerDto.getMerchantId()).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customerDto.getCustomerId()).customerResponse(buildEncryptCustomerResponse(customerDto, merchantDto)).build();
        return TransactionResponse.<CustomerResponse>builder().data(List.of(customerResponse)).status(1).count(1L).build();
    }

    private String buildEncryptCustomerResponse(CustomerDto customerDto, MerchantDto merchantDto) {
        String customerResponseString = objectMapper.convertValue(customerDto, String.class);
        return encryptionDecryptionUtil.encryptByKMS(customerResponseString, merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek());
    }

    private CustomerDto buildCustomerDTO(CustomerRequest customerRequest, MerchantDto merchantDto) {
        String decryptedCustomerRequest = encryptionDecryptionUtil.decryptByKMS(customerRequest.getCustomerRequest(), merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek());
        return objectMapper.convertValue(decryptedCustomerRequest, CustomerDto.class);
    }

}

