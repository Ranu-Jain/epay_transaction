package com.epay.transaction.util;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.repository.CustomerRepository;
import com.epay.transaction.repository.OrderRepository;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class Name:UniqueIDGenerator
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
@RequiredArgsConstructor
@Component
public class UniqueIDGenerator {

    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int ALPHABET_LENGTH = ALPHABET.length();
    private final int NANOS_LENGTH = 15;
    private final int ALPHA_LENGTH = 5;
    private static final AtomicLong lastTime = new AtomicLong(0);
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    LoggerUtility logger = LoggerFactoryUtility.getLogger(UniqueIDGenerator.class);

    /**
     * This method of generating customer ID
     */
    public String generateUniqueCustomerId() {
        logger.info("CustomerId generation initiated");
        int retryCount = 1;
        String customerId = "Cust_" + RandomStringUtils.randomAlphanumeric(16);
        while (retryCount < 3 && customerRepository.existsByCustomerId(customerId)) {
            customerId = "Cust_" + RandomStringUtils.randomAlphanumeric(16);
            retryCount++;
        }
        if (retryCount == 3 && customerRepository.existsByCustomerId(customerId)) {
            throw new TransactionException(ErrorConstants.ALREADY_EXIST_ERROR_CODE, MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, "customerId"));
        }
        logger.info("CustomerId created successfully");
        return customerId;
    }

    public String generateSBIOrderRefNumber() {
        logger.info("SBIOrderRefNumber generation initiated");
        int retryCount = 1;
        String sbiOrderRefNumber = getRefNumber();
        while (retryCount < 3 && orderRepository.existsBySbiOrderRefNumber(sbiOrderRefNumber)) {
            sbiOrderRefNumber = getRefNumber();
            retryCount++;
        }
        if (retryCount == 3 && orderRepository.existsBySbiOrderRefNumber(sbiOrderRefNumber)) {
            throw new TransactionException(ErrorConstants.ALREADY_EXIST_ERROR_CODE, MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, "SBIOrderRefNumber"));
        }
        logger.info("SBIOrderRefNumber created successfully");
        return sbiOrderRefNumber;
    }

    private String getRefNumber() {
        Random random = new Random();
        String nanosString = String.format("%015d", getUniqueTime()).substring(0, NANOS_LENGTH);
        return IntStream.range(0, ALPHA_LENGTH).mapToObj(i -> String.valueOf(ALPHABET.charAt(random.nextInt(ALPHABET_LENGTH)))).collect(Collectors.joining("", nanosString, ""));
    }

    private long getUniqueTime() {
        while (true) {
            long previousTime = lastTime.get();
            long currentTime = System.nanoTime();
            if (currentTime > previousTime) {
                if (lastTime.compareAndSet(previousTime, currentTime)) {
                    return currentTime;
                }
            } else {
                long newTime = previousTime + 1;
                if (lastTime.compareAndSet(previousTime, newTime)) {
                    return newTime;
                }
            }
        }
    }

}