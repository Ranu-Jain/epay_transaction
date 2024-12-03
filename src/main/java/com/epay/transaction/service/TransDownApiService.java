package com.epay.transaction.service;

import com.epay.transaction.dao.TransDownApiDao;
import com.epay.transaction.model.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Class Name: TransDownApiService
 * *
 * Description: This class contains details of transaction downtime api details.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Service
@AllArgsConstructor
public class TransDownApiService {
    private final TransDownApiDao transDownApiDao;
    private final String status ="L";

    /**
     * This methods contains transaction downtime api details.
     *
     * @return list of transaction downtime api details.
     */

    public TransactionResponse<Object> getDowntimeApiDetails() {
        return transDownApiDao.getDowntimeApiDetails(status);
    }

}

