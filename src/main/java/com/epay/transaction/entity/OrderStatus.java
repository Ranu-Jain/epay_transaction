package com.epay.transaction.entity;

import jakarta.persistence.EnumType;


/**
 * enum Name: OrderStatus
 * *
 * Description: OrderStatus enum for order status.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
public enum OrderStatus {
    created,attempted,cancelled,expired,attempt,paid,failed;

}
