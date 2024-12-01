package com.epay.transaction.util;

import lombok.*;

import java.util.List;
/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApiResponse<T> {
    private List<T> data;
    private int count;
    private int size;
    private List<ErrorDto> errors;
    private String status;
}


