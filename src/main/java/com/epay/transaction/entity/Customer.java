package com.epay.transaction.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

/**
 * Class Name: CustomerController
 * *
 * Description: Customer creation for given Merchant.
 * *
 * Author: V1018400 (Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Data
@Entity
@Table(name="merchant_customer")
public class Customer {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @NotBlank(message="Merchant Id is required")
  @Column(name="merchant_id")
  private String mid;

  @Column(name="customer_id")
  private String customerId;

  @Column(name="Name")
  private String name;

  @Email(message = "please enter valid email")
  @Column(name="Email")
  private String email;

  @Pattern(regexp = "^\\+?\\d{10,15}",message = "Invalid phone number,it should be minimum 10 and max 15")
  @Column(name="phone_number")
  private String phoneNumber;

  private String gstin;
  private String gstin_address;

  @Column(name="created_by")
  private String createdBy;

  @Column(name="updated_by")
  private String updatedBy;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="created_date")
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="updated_date")
  private Date updatedDate;


  private String prefpaymode;
  private String address1;
  private String address2;

  private String city;
  private String state;
  private String country;

  @Pattern(regexp = "^\\+?[0-9]{6}",message = "Invalid pin code")
  private String pinCode;

}
