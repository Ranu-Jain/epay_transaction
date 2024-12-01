package com.epay.transaction.model.request;

import lombok.Data;

/**
 * Class Name:PaymentRequest
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
public class PaymentRequest {
String orderReferenceNo;
String paymode;
Double amount;
}
