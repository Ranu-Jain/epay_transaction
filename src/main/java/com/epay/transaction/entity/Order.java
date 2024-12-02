package com.epay.transaction.entity;

import com.epay.transaction.util.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "merchant_id")
    private String mId;

    private String customerId;

    private String currencyCode;

    private Double orderAmount;

    private String orderRefNumber;

    private String sbiOrderRefNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "other_details", columnDefinition = "CLOB")
    private String otherDetails;
    private Integer expiry;

    @Column(name = "multi_accounts", columnDefinition = "CLOB")
    private String multiAccounts;

    private String paymentMode;

    private String orderHash;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "updated_date")
    private Long updatedDate;

}