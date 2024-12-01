package com.epay.transaction.model.response;

import com.epay.transaction.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private String orderId;
    private OrderStatus status;
    private String mId;
    private Double amount;
    private String customerId;
    private String currency;
    private String merchantOrderNumber;
    private String bankOrderNumber;
    private String paymentMode;
    private String successFailURL;
    private String otherDetails;
    private String multiAccount;
    private String thirdPartyValidation;
    private String gatewayMapID;
    private String paymentURL;
    private int attempts;
    private long createdAt;




}
