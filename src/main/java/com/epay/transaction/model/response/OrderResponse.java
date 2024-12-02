package com.epay.transaction.model.response;

import lombok.*;


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
    private String orderRefNum;
    private String orderResponse;
}
