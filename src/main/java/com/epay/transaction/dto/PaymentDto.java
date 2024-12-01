package com.epay.transaction.dto;

import lombok.Data;

/**
 * Class Name:PaymentDto
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Data
public class PaymentDto {
    private String id;
    private String status;

    private String merchantTokenId;
    private String mid;
   private String atrn;
   private  String cin;
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
    private String token;
    private Long createdDate;
}
