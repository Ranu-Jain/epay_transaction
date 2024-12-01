package com.epay.transaction.util;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
    private String reason;

}
