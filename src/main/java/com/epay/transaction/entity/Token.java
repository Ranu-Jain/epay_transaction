/**
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 * <p>
 * Author:@V0000001(Shilpa Kothre)
 * <p>
 * Version:1.0
 * /
 */

package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token", indexes = {@Index(name = "idx_merchant_id", columnList = "merchant_id")})
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "merchant_id", nullable = false)
    private String merchantId;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType;

    @Column(name = "generated_token")
    private String generatedToken;

    @Column(name = "token_expiry_time")
    private Long tokenExpiryTime; // Using Long to store number of milliseconds

    @Column(name = "is_token_valid", nullable = false)
    private boolean isTokenValid;

    @Column(name = "failed_reason", length = 100)
    private String failedReason;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "expired_at")
    private Long expiredAt;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

}
