package com.epay.transaction.util;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.repositary.CustomerRepository;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * Class Name:UniqueIDGenearator
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
@Data
@Component
public  class UniqueIDGenearator {

    private final CustomerRepository customerRepository;

    public UniqueIDGenearator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * This method of generating customer ID
     * @return
     */
    public  String generateUniqueCustomerId() {
        int retryCount = 0;
        String customerId;
        do {
            customerId = "Cust_" + RandomStringUtils.randomAlphanumeric(16);
            retryCount++;
        } while (retryCount < 3 && customerRepository.existsByCustomerId(customerId));
        if (retryCount == 3 && customerRepository.existsByCustomerId(customerId)) {
            throw new TransactionException("","");
        }
        return customerId;
    }
}


