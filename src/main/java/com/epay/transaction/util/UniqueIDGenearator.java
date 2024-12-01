package com.epay.transaction.util;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.repositary.CustomerRepository;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import java.text.MessageFormat;

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

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int ALPHABET_LENGTH = ALPHABET.length();

    private static final int NANOS_LENGTH = 15;

    private static final int ALPHA_LENGTH = 5;

    // Ensure the sequence is unique within a single JVM
    private static final AtomicLong lastTime = new AtomicLong(0);


LoggerUtility logger= LoggerFactoryUtility.getLogger(UniqueIDGenearator.class);
    private final CustomerRepository customerRepository;

    public UniqueIDGenearator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * This method of generating customer ID
     * @return
     */
    public  String generateUniqueCustomerId() {
        logger.info("CutomerId generation intiated");
        int retryCount = 0;
        String customerId;
        do {
            customerId = "Cust_" + RandomStringUtils.randomAlphanumeric(16);
            retryCount++;
        } while (retryCount < 3 && customerRepository.existsByCustomerId(customerId));
        if (retryCount == 3 && customerRepository.existsByCustomerId(customerId)) {
            throw new TransactionException(ErrorConstants.ALREADY_EXIST_ERROR_CODE, MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, "customerId"));
        }
        logger.info("CutomerId created");
        return customerId;
    }

    public String generateOrderRequestId() {

        long currentTime = System.nanoTime();

        long uniqueTime = ensureUniqueTime(currentTime);

        String nanosString = String.format("%015d", uniqueTime).substring(0, NANOS_LENGTH);

        Random random = new Random();

        StringBuilder sb = new StringBuilder(ALPHA_LENGTH);

        for (int i = 0; i < ALPHA_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET_LENGTH)));
        }

        return nanosString + sb.toString();
    }

    private long ensureUniqueTime(long currentTime) {

        // Atomically update the last time to ensure uniqueness
        long previousTime;
        do {
            previousTime = lastTime.get();
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
        } while (true);
    }

}