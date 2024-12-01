package com.epay.transaction.dto;

import lombok.*;

import java.util.UUID;

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
public class OrderDto {

    private String id;
    private String status;
    private String hash;
    private String merchantTokenId;
    private String mid;
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
    private String systemIp;
    private String geoLocation;
    private String systemDetails;
    private String orderGenerationMode;
    private String otherDetails;
    private long expiry;
    private String tpv;
    private String token;

}
