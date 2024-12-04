
package com.epay.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDto {
    private String mID;
    //private Long accessTokenExpiryTime;
   // private Long transactionTokenExpiryTime;
    private Integer accessTokenExpiryTime;
    private Integer transactionTokenExpiryTime;
    private String status;
    private String apiKey;
    private String secretKey;
    private String mek;
    private String kek;
    private String aek;
}
