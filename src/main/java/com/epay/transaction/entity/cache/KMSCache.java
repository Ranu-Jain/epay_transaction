package com.epay.transaction.entity.cache;

import com.sbi.epay.hazelcast.model.EPayCachebleData;
import lombok.Builder;
import lombok.Data;

/**
 * Class Name: KMSCache
 * *
 * Description: This class contains entity/variables related to Key_management database tables.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Data
@Builder
public class KMSCache implements EPayCachebleData {


    /**
     * merchantId representing merchant ID and linking to the Merchant table
     */
    private String merchantId;

    /**
     * encryptedKey representing Encryption Key.
     */
    private String encryptedMEK;

    /**
     * encryptedKey representing Encryption Key.
     */
    private String encryptedKEK;


}
