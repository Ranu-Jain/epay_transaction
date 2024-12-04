package com.epay.transaction.repository.cache;

import com.epay.transaction.dto.DowntimeAPIDto;
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
 * Class Name: DowntimeAPICacheRepository
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
public class DowntimeAPICacheRepository {
    private static final String DOWNTIME_API_MAP = "downtime_details";
    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(DowntimeAPICacheRepository.class);
    private final HazelcastService hazelcastService;
    private final HazelcastInstance hazelcastInstance;
    private final ObjectMapper objectMapper;

    /**
     * Get downtime api details from cache
     *
     * @param status as string
     * @return list of downtime apis.
     */
    public Optional<List<DowntimeAPIDto>> getDowntimeAPIs(String status) {
        logger.debug("getDowntimeAPIs : status {}", status);
        //TODO: Currently setting downtime list as empty because cache is not implemented, will be removed empty-list after cache implementation.
        //return Optional.of(List.of(objectMapper.convertValue(hazelcastService.getDataBySql(DOWNTIME_API_MAP, "__status like" + status, hazelcastInstance).getCacheableEntityData(), DowntimeAPIDto.class)));
        return Optional.of(Collections.emptyList());
    }
}
