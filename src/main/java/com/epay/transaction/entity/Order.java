package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "merchant_order_info")
public class Order {

    @Id
    private String id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(name = "order_hash")
    private String hash;

    @Column(name = "merchant_token_id")
    private String merchantTokenId;

    @Column(name = "merchant_id")
    private String mid;

    @Column(name = "merchant_customer_id")
    private String merchantCustomerId;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_ref_num")
    private String orderRefNum;

    @Column(name = "operation_mode")
    private String operationMode;

    @Column(name = "txn_mode")
    private String txnMode;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "access_mode")
    private String accessMode;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_request_count")
    private Integer orderRequestCount;

    @Column(name = "callback_url")
    private String callbackUrl;

    @Column(name = "failed_reason")
    private String failedReason;

    @Column(name = "system_ip")
    private String systemIp;

    @Column(name = "geo_location")
    private String geoLocation;

    @Column(name = "system_details")
    private String systemDetails;

    @Column(name = "order_generation_mode")
    private String orderGenerationMode;

    @Column(name = "other_details", columnDefinition = "CLOB")
    private String otherDetails;

    @Column(name = "expiry")
    private Integer expiry;

    @Column(name = "tpv", columnDefinition = "CLOB")
    private String tpv;

    @Column(name = "token")
    private String token;

}