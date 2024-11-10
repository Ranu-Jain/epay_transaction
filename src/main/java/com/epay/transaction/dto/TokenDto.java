/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *
 *  Version:1.0
 * /
 */

package com.epay.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class TokenDto {
    private UUID id;
    private String merchantId;
    private String tokenType;
    private String generatedToken;
    private Long tokenExpiryTime;
    private boolean isTokenValid;
    private String failedReason;
    private String remarks;
    private Long expiredAt;
    private String status;
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
}
