
package com.epay.transaction.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {

    private UUID id;
    private String mId;
    private String customerId;
    private String name;
    private String email;
    private String gstIn;
    private String gstInAddress;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String status;
    private String createdBy;
    private String updatedBy;
    private Long createdDate;
    private Long updatedDate;



}
