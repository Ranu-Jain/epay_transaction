package com.epay.transaction.repository.cache;

import com.epay.transaction.dto.TransDownApiDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.sbi.epay.hazelcast.service.HazelcastService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class Name: TransDownApiCacheRepository
 * *
 * Description: This class represents record(s) related to transaction downtime apis from cache.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Component
@AllArgsConstructor
public class TransDownApiCacheRepository {
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(TransDownApiCacheRepository.class);
    private static final String DOWNTIME_API_MAP = "downtime_details";
    private final HazelcastService hazelcastService;
    private final HazelcastInstance hazelcastInstance;
    private final ObjectMapper objectMapper;

    /**
     * Get downtime api details from cache
     *
     * @param status as string
     * @return list of downtime apis.
     */
    public Optional<List<TransDownApiDto>> getDowntimeApiDetails(String status) {
        //TODO: Currently setting downtime list as empty because cache is not implemented, will be removed try-catch after cache implementation.
        try{
            return Optional.of(List.of(objectMapper.convertValue(hazelcastService.getDataBySql(DOWNTIME_API_MAP, "__status like" + status, hazelcastInstance)
                    .getCacheableEntityData(), TransDownApiDto.class)));
        } catch (RuntimeException e) {
            logger.error("Error -> :",e);
           return Optional.of(Collections.emptyList());
        }


    }
}
