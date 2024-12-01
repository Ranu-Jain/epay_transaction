package com.epay.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Class Name: KeyManagement
 * *
 * Description: This class is used for create entity for key management
 * *
 * Author: V1018344(Bhushan Wadekar)
 * Copyright (c) 2024 [State Bank of India]
 * ALl rights reserved
 * *
 * Version: 1.0
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "key_management")
public class KeyManagement {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "id", nullable = false, updatable = false, unique = true)
   private UUID id;

   @Column(name = "merchant_id", nullable = false)
   private String merchantId;

   @Column(name = "key_type")
   private String keyType;

   @Column(name = "encrypted_mek")
   private String encryptedMek;

   @Column(name = "encrypted_kek")
   private String encryptedKek;

   @Column(name = "expiry_time")
   @Temporal(TemporalType.TIMESTAMP)
   private long expiryTime;

   @Column(name = "key_encryption_algo")
   private String keyEncryptionAlgo;

   @Column(name = "status")
   private String status;

   @Column(name = "created_date", nullable = false)
   private long createdDate;

   @Column(name = "updated_date", nullable = false)
   private long updatedDate;

   @Column(name = "created_by")
   private String createdBy;

   @Column(name = "updated_by")
   private String updatedBy;


}
