package com.epay.transaction.dto;

import com.epay.transaction.util.enums.*;
import lombok.Data;

import java.util.UUID;

/**
 * Class Name:TransactionDto
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
public class TransactionDto {

    private UUID id;
    private String merchantId;

    /* Order Information */
    private String orderRefNumber;
    private String sbiOrderRefNumber;

    /* Payment Information */
    private String payMode;
    private String paymentModeDetails;
    private Double debitAmt;
    private String channelBank;
    private String cin;
    private String pushResponse;

    /* Transaction Information */
    private Long txnRequestCount;
    private String failReason;
    private String atrnNumber;

    /* Status Information */
    private TransactionStatus status;
    private PaymentStatus paymentStatus;
    private SettelmentStatus settlementStatus;
    private RefundStatus refundStatus;
    private CancellationStatus cancellationStatus;

    /* Audit Information */
    private String createdBy;
    private String updatedBy;
    private Long createdDate;
    private Long updatedDate;
}
