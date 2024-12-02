package com.epay.transaction.dto;

import lombok.*;

/**
 * Class Name: OrderDto
 * *
 * Description: OrderDto class for Object made to transfer to Order reposiotry.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto extends BaseDto {
    private String id;
    private String mid;
    private String hash;
    private String merchantCustomerId;
    private String countryId;
    private String currencyId;
    private Double amount;
    private String orderRefNum;
    private String sbiOrderRefNum;
    private String operationMode;
    private String txnMode;
    private String paymentMode;
    private String accessMode;
    private String orderStatus;
    private Integer orderRequestCount;
    private String callbackUrl;
    private String failedReason;
    private String otherDetails;
    private long expiry;
}
