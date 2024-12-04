package com.epay.transaction.dao;

import com.epay.transaction.dto.DowntimeAPIDto;
import com.epay.transaction.externalservice.AdminServicesClient;
import com.epay.transaction.repository.cache.DowntimeAPICacheRepository;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class Name: DowntimeAPIDao
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
public class DowntimeAPIDao {
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(DowntimeAPIDao.class);
    private final DowntimeAPICacheRepository downtimeAPICacheRepository;
    private final AdminServicesClient adminServicesClient;

    /**
     * Fetch record(s) of transaction downtime apis from db table.
     *
     * @param status as String
     * @return list of downtime apis.
     */
    public List<DowntimeAPIDto> getDowntimeAPIs(String status) {
        List<DowntimeAPIDto> downtimeAPIs = downtimeAPICacheRepository.getDowntimeAPIs(status).orElseThrow();
        if (downtimeAPIs.isEmpty()) {
            logger.info("Transaction downtime api details found empty in cache and called from database tables.");
            downtimeAPIs = adminServicesClient.getDowntimeAPIs();
        }
        return downtimeAPIs;
    }
}
