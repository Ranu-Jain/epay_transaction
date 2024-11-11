package com.epay.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerDto {

    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gstIn;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String customerId;
    private String status;
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;

}
