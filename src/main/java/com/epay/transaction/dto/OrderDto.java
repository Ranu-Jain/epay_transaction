package com.epay.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderDto {

    private UUID id;
    private String status;
    private String hash;
    private UUID merchantTokenId;
    private String mID;
    private UUID merchantCustomerId;
    private String countryId;
    private String currencyId;
    private Double amount;
    private String orderRefNum;
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
    private Integer expiry;
    private String tpv;


}
