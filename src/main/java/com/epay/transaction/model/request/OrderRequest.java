package com.epay.transaction.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
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
@Data
public class OrderRequest {
    @NotNull
    private String orderRequest;
}