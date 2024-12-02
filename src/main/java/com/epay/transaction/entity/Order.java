package com.epay.transaction.entity;

import com.epay.transaction.util.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "merchant_order_info")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String orderHash;
    @Column(name = "merchant_id")
    private String mId;
    private String customerId;
    private String currencyId;
    private Double amount;
    private String orderRefNum;
    private String operationMode;
    private String paymentMode;
    @Column(name = "callback_url")
    private String callbackUrl;
    @Column(name = "failed_reason")
    private String failedReason;
    @Column(name = "other_details", columnDefinition = "CLOB")
    private String otherDetails;
    @Column(name = "expiry")
    private Integer expiry;

}