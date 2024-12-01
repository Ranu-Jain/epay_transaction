package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Class Name: KeyAudit
 * *
 * Description: This class represents entity/variables related key_audit database table.
 * *
 * Author: V1018217
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
@Table(name = "key_audit")
public class KeyAudit
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "key_id")
    private UUID keyId;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "action_details")
    private String actionDetails;
}
