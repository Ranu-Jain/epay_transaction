package com.epay.transaction.dto;

import lombok.*;

import java.util.UUID;

/**
 * Class Name:TokenAuditDto
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenAuditDto {
    private UUID id;
    private UUID tokenId;
    private String actionType;
}

