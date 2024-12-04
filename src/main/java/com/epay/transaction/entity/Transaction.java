package com.epay.transaction.entity;

import com.epay.transaction.util.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Class Name:Transaction
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
@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    private String merchantId;

    /* Order Information */
    @Column(name = "order_ref_num")
    private String orderRefNumber;
    @Column(name = "sbi_order_ref_num")
    private String sbiOrderRefNumber;

    /* Payment Information */
    private String payMode;
    @Lob
    private String paymentModeDetails;
    @Column(name = "debit_amt")
    private Double debitAmt;
    @Column(name = "channel_bank")
    private String channelBank;
    private String cin;
    @Lob
    @Column(name = "push_response")
    private String pushResponse;

    /* Transaction Information */
    @Column(name = "txn_request_count")
    private Long txnRequestCount;
    @Column(name = "fail_reason")
    private String failReason;
    @Column(name = "atrn_num")
    private String atrnNumber;

    /* Status Information */
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private SettelmentStatus settlementStatus;
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;
    @Enumerated(EnumType.STRING)
    private CancellationStatus cancellationStatus;

    /* Audit Information */
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_date")
    private Long createdDate;
    @Column(name = "updated_date")
    private Long updatedDate;
}