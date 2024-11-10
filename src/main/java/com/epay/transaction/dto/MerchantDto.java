package com.epay.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDto {
    private String mID;
    private Long accessTokenExpiryTime;
    private String status;
}
