/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */


package com.epay.transaction.util;

public class ErrorConstants {

    public static final String MANDATORY_FOUND_ERROR_CODE = "1001";
    public static final String MANDATORY_ERROR_MESSAGE = "{0} is mandatory.";
    public static final String NOT_FOUND_ERROR_CODE = "1003";
    public static final String NOT_FOUND_ERROR_MESSAGE = "{0} is not found.";
    public static final String INVALID_ERROR_CODE = "1002";
    public static final String INVALID_ERROR_MESSAGE = "{0} is invalid. Reason : {1}";
    public static final String ALREADY_EXIST_ERROR_CODE = "1004";
    public static final String ALREADY_EXIST_ERROR_MESSAGE = "{0} is already exist.";

    private ErrorConstants() {
    }
}
