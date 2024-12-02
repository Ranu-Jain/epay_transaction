package com.epay.transaction.dao.cache;


import com.epay.transaction.entity.cache.KMSCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.sbi.epay.hazelcast.model.CacheableEntity;
import com.sbi.epay.hazelcast.service.HazelcastService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

/**
 * Class Name: KMSCacheDao
 * *
 * Description: This class contains dao of cache management.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@RequiredArgsConstructor
@Component
public class KMSCacheDao {
    LoggerUtility logger = LoggerFactoryUtility.getLogger(this.getClass());
    private static final String KMS_MAP = "key_management";
    private final HazelcastService hazelcastService;
    private final HazelcastInstance hazelcastInstance;
    private final ObjectMapper objectMapper;

    /**
     * Save record(s) related to key_management details into cache.
     * <p>
     * Params KMSCache
     */
    public void saveKeys(KMSCache kmsCache) {
        CacheableEntity cacheableEntity = CacheableEntity.builder().key(String.valueOf(kmsCache.getMerchantId())).mapName(KMS_MAP).cacheableEntityData(kmsCache).build();
        String response = hazelcastService.addDataToCache(cacheableEntity, hazelcastInstance);
        logger.info("keys details saved into cache : {}", response);
    }

    /**
     * get record(s) from keyManagement by merchant ID.
     *
     * @return record(s) of key management.
     */
    public KeyResponse getKeysByMerchantId(String merchantId) {
        CacheableEntity cacheableEntity = hazelcastService.getDataByKey(KMS_MAP, merchantId, hazelcastInstance);
        val kmsCache = cacheableEntity.getCacheableEntityData();
        return objectMapper.convertValue(kmsCache, KeyResponse.class);
    }
}
