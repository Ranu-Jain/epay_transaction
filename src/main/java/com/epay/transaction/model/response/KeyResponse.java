package com.epay.transaction.model.response;

import lombok.Data;

/**
 * Class Name: KeyResponse
 * *
 * Description:
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Data
public class KeyResponse {

    private String merchantId;
    private String encryptedMEK;
    private String encryptedKEK;
    private String aek;
}
