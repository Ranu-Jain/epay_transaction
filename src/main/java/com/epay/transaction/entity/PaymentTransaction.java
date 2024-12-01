package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Clob;
import java.util.UUID;

/**
 * Class Name:PaymentTransaction
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
@Table(name = "Merchant_Payment_Txn")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String uuid;
  @Column(name = "merchant_order_id",nullable = false, updatable = false,unique = true)
UUID orderId;
  @Column(name="merchant_token_id",nullable = false, updatable = false,unique = true)
UUID transactionId;
    @Column(name="atrn_num")
  String atrn;

    String payMode;
    Clob PaymentModeDetails;
    String Paymentstatus;
    Long txnRequestCount;
    String failReason;
    String debitAmt;
    String gstin;
    String chanelBank;
    @Column(name="order_ref_num")
    String order_ref_num;
    @Column(name="settelment_status")
   String settlestatus;
    String refundStatus;
   String  cancellationStatus;
    String cin;
    Clob pushResponse;
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Long createdDate;

    ///@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Long updatedDate;
}
