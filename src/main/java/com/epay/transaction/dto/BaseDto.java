package com.epay.transaction.dto;

import lombok.Data;

@Data
public class BaseDto {
    private String createdBy;
    private String updatedBy;
    private Long createdDate;
    private Long updatedDate;
}
