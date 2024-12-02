package com.epay.transaction.dto;

import com.epay.transaction.util.enums.OrderStatus;
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
public class OrderDto extends BaseDto {
    private UUID id;
    private String mId;
    private String customerId;
    private String currencyCode;
    private Double orderAmount;
    private String orderRefNumber;
    private String sbiOrderRefNumber;
    private OrderStatus status;
    private String otherDetails;
    private Integer expiry;
    private String multiAccounts;
    private String paymentMode;
    private String orderHash;
}
