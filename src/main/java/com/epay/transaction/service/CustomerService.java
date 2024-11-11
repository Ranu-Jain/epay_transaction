package com.epay.transaction.service;

/*
  Class Name:CustomerService
  *
  Description:
  *
  Author:V1014352(Ranjan Kumar)
  <p>
  Copyright (c) 2024 [State Bank of INdia]
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
import com.epay.transaction.util.UniqueIDGenearator;
import com.epay.transaction.validator.CustomerValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.UserInfo;
import com.sbi.epay.util.util.EncryptionDecryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;
    private final CustomerValidator customerValidator;

    public TransactionResponse<CustomerResponse> saveCustomer(CustomerRequest customerRequest) {
        //Step 1 : Get Merchant for finding the Keys
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String mID = userInfo.getUsername();
        MerchantDto merchantDto = customerDao.getActiveMerchantByMID(mID).orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
        //Step 2 : Decrypt the customerRequest
        EncryptionDecryptionUtil encryptionDecryptionUtil = new EncryptionDecryptionUtil();
        String decryptedCustomerRequest = encryptionDecryptionUtil.decryptByKMS(customerRequest.getCustomerRequest(), merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek());
        CustomerDto customerDto = objectMapper.convertValue(decryptedCustomerRequest, CustomerDto.class);
        //Step 3 : Validated customerRequest
        customerValidator.validateCustomerRequest(customerDto);
        //Step 4 : Generate CustomerId
        customerDto.setCustomerId(uniqueIDGenearator.generateUniqueCustomerId());
        //Step 5 : Save Customer Data
        customerDto = customerDao.saveCustomer(customerDto);
        //Step 6 : Encrypt the Customer Request with MEK
        String customerResponseString = objectMapper.convertValue(customerDto, String.class);
        String encryptedCustomerResponse = encryptionDecryptionUtil.encryptByKMS(customerResponseString, merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek());
        //Step 7 : Build Customer Response and send to controller
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customerDto.getCustomerId()).customerResponse(encryptedCustomerResponse).build();
        return TransactionResponse.<CustomerResponse>builder().data(List.of(customerResponse)).status(1).count(1L).build();
    }

}

