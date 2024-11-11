package com.epay.transaction.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Class Name:CustomerRequest
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of India]
 * All right reserved
 * *
 * Version:1.0
 */
@Data
public class CustomerRequest {
    @NotNull
    private String customerRequest;
}
