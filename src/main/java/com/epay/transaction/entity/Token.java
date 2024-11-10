/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *
 *  Version:1.0
 * /
 */

package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token", indexes = {
        @Index(name = "idx_merchant_id", columnList = "merchant_id")
})
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false,unique = true)
    private UUID id;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType;

    @Column(name = "generated_token",  length = 200)
    private String generatedToken;

    @Column(name = "token_expiry_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpiryTime;

    @Column(name = "is_token_valid", nullable = false)
    private boolean isTokenValid;

    @Column(name = "failed_reason", length = 100)
    private String failedReason;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "expired_at" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
