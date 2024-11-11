package com.epay.transaction.service;

/**
 * Class Name:CustomerService
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

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.entity.Customer;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.CustomerResponse;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.UniqueIDGenearator;
import com.epay.transaction.validator.CustomerValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.authentication.model.UserInfo;
import com.sbi.epay.util.service.DecryptionService;
import com.sbi.epay.util.util.EncryptionDecryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final UniqueIDGenearator uniqueIDGenearator;
    private final ObjectMapper objectMapper;
    private final CustomerValidator customerValidator;

    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
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
        uniqueIDGenearator.generateUniqueCustomerId();
        //Step 5 : Save Customer Data
        //Step 6 : Encrypt the Customer Request with MEK
        //Step 7 : Build Customer Response and send to controller
        //customer.setCustomerId();


        //Customer savedCustomer = customerDao.saveCustomer(customer);


        // Success Response for new customer

        return null;//new CustomerResponse(savedCustomer.getId(), savedCustomer.getCustomerId());


    }

}

