package com.epay.transaction.repository;

import com.epay.transaction.dto.DowntimeAPIDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Class Name: DowntimeAPIRepository
 * *
 * Description: This class contains repository details of downtime api.
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Component
@AllArgsConstructor
public class DowntimeAPIRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String PAY_GTW_ID = "PAYGTWID";
    private final String PAY_GTW_NAME = "PAYGTWNAME";
    private final String PAY_MODE_CODE = "PAYMODECODE";
    private final String DOWN_START_DATE_TIME = "DOWNTIMESTARTDATETIME";
    private final String DOWN_END_DATE_TIME = "DOWNTIMEENDDATETIME";

    /**
     * Get downtime api details from db tables
     *
     * @param status as string
     * @return list of downtime api details.
     */
    public Optional<List<DowntimeAPIDto>> getDowntimeAPIs(String status) {
        String query = "SELECT a.PAYGTWID, b.PAYGTWNAME, a.PAYMODECODE, a.DOWNTIMESTARTDATETIME, a.DOWNTIMEENDDATETIME " +
                "from AGGPAYGATEWAYDOWNTIMEDTLS a JOIN PAYMENTGATEWAYMASTER b ON a.PAYGTWID = b.PAYGTWID WHERE a.STATUS='" + status + "'";
        return Optional.of(jdbcTemplate.query(query, (rs, rowNum) -> new DowntimeAPIDto(rs.getString(PAY_GTW_ID), rs.getString(PAY_GTW_NAME),
                rs.getString(PAY_MODE_CODE), rs.getString(DOWN_START_DATE_TIME), rs.getString(DOWN_END_DATE_TIME))));
    }
}
