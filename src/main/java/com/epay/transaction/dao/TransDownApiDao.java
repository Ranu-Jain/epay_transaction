package com.epay.transaction.dao;

import com.epay.transaction.dto.TransDownApiDto;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.repository.TransDownApiRepository;
import com.epay.transaction.repository.cache.TransDownApiCacheRepository;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class Name: TransDownApiDao
 * *
 * Description: This class represents methods related to transaction downtime api details.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Component
@AllArgsConstructor
public class TransDownApiDao {
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(TransDownApiDao.class);
    private final TransDownApiCacheRepository transDownApiCacheRepository;
    private final TransDownApiRepository transDownApiRepository;

    /**
     * Fetch record(s) of transaction downtime apis from db table.
     *
     * @param status as String
     * @return list of downtime apis.
     */
    public TransactionResponse<Object> getDowntimeApiDetails(String status) {
        List<TransDownApiDto> cacheResponse = transDownApiCacheRepository.getDowntimeApiDetails(status).orElseThrow();
        if (cacheResponse.isEmpty()) {
            logger.info("Transaction downtime api details found empty in cache and called from database tables.");
            cacheResponse = transDownApiRepository.getDowntimeApiDetails(status).orElseThrow();
        }
        return TransactionResponse.builder().data(List.of(cacheResponse)).build();
    }
}
