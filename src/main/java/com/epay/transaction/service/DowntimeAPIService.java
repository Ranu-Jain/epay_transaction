package com.epay.transaction.service;

import com.epay.transaction.dao.DowntimeAPIDao;
import com.epay.transaction.dto.DowntimeAPIDto;
import com.epay.transaction.model.response.TransactionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Class Name: DowntimeAPIService
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
public class DowntimeAPIService {
    private final DowntimeAPIDao downtimeAPIDao;

    /**
     * This methods contains transaction downtime api details.
     *
     * @return list of transaction downtime api details.
     */

    public TransactionResponse<DowntimeAPIDto> getDowntimeAPIs() {
        String status = "L";
        List<DowntimeAPIDto> downtimeApiDetails = downtimeAPIDao.getDowntimeAPIs(status);
        return TransactionResponse.<DowntimeAPIDto>builder().data(downtimeApiDetails).status(1).count(1L).build();
    }

}

