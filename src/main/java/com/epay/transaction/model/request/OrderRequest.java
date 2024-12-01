package com.epay.transaction.model.request;

import lombok.Getter;
import lombok.Setter;


/**
 * Class Name: OrderRequest
 * *
 * Description: OrderRequest class for handel order request.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Setter
@Getter
public class OrderRequest {
    private String orderid;
    private String mid;
    private Double amount;
    private String  customerId;
    private String  currency;
    private String  merchantOrderNumber;
    private String  paymentMode;
    private String  successFailURL;
    private String  otherDetails;
    private String multiAccount;
    private String thirdPartyValidation;
    private String  gatewayMapID;
    private String  paymentLink;
    private String orderHash;
    private String status;
}