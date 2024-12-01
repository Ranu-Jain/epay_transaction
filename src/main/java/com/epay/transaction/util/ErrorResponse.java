package com.epay.transaction.util;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */

import lombok.Data;

import java.util.List;
@Data
public class ErrorResponse {
    private List<Error> errors;
    private int status;

    public ErrorResponse(String number, String s, String s1) {
    }
}
