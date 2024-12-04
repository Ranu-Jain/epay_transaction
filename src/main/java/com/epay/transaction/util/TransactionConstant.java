package com.epay.transaction.util;

/**
 * Class Name:TransactionConstant
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
public class TransactionConstant {

    public static final int NAME_LENGTH = 100;
    public static final int EMAIL_LENGTH = 255;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "^\\d{10}$";
    public static final String PIN_REGEX = "^\\d{6}$";
    public static final String GSTIN_REGEX = "\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";
    public static final String ALLOWED_CHARACTERS_REGEX = "^[A-Za-z0-9@#$*_\\-\\)\\(]+$";
}
