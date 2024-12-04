package com.epay.transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class Name: DowntimeAPIDto
 * *
 * Description: This response class contains details related to transaction- downtime api records(s).
 * *
 * Author: V1018217
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DowntimeAPIDto {
    private String payGtwId;
    private String payGtwName;
    private String payModeCode;
    private String downTimeStartDatetime;
    private String downTimeEndDatetime;
}
