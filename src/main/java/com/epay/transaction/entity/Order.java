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
@Table(name = "merchant_order_info")
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(nullable = false, updatable = false)
   private UUID id;

   @Column(name = "status", length = 50)
   private String status;

   @Column(name = "order_hash", length = 50)
   private String hash;

   @Column(name = "merchant_token_id")
   private UUID merchantTokenId;

   @Column(name = "merchant_id")
   private UUID mID;

   @Column(name = "merchant_customer_id")
   private UUID merchantCustomerId;

   @Column(name = "country_id", length = 50)
   private String countryId;

   @Column(name = "currency_id", length = 50)
   private String currencyId;

   @Column(name = "amount")
   private Double amount;

   @Column(name = "order_ref_num")
   private String orderRefNum;

   @Column(name = "operation_mode", length = 50)
   private String operationMode;

   @Column(name = "txn_mode", length = 50)
   private String txnMode;

   @Column(name = "payment_mode", length = 50)
   private String paymentMode;

   @Column(name = "access_mode", length = 50)
   private String accessMode;

   @Column(name = "order_status", length = 50)
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

   @Column(name = "system_details", length = 50)
   private String systemDetails;

   @Column(name = "order_generation_mode", length = 50)
   private String orderGenerationMode;

   @Column(name = "other_details", columnDefinition = "CLOB")
   private String otherDetails;

   @Column(name = "expiry")
   private Integer expiry;

   @Column(name = "tpv", columnDefinition = "CLOB")
   private String tpv;

}
