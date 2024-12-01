package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Class Name: KeyExpiry
 * *
 * Description:
 * *
 * Author: V1017903(bhushan wadekar)
 * <p>
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "key_expiry")
public class KeyExpiry
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * Id representing as UUID. Primary key, unique identifier for each expiry record.
     */
    @Column(name = "key_id")
    private UUID keyId;

    @Column(name="expired_at")
    private Long expiredAt;

    @Column(name = "expiry_action")
    private String expiryAction;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private long createdDate;

    @Column(name = "created_by")
    private String createdBy;
}
