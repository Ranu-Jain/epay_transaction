package com.epay.transaction.util.enums;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.util.ErrorConstants;

import java.text.MessageFormat;
import java.util.Arrays;

public enum Status {
    ACTIVE, INACTIVE, BLOCKED, DELETE;

    public static Status getStatus(String status) {
        return Arrays.stream(values()).filter(s -> s.name().equalsIgnoreCase(status)).findFirst()
                .orElseThrow(() ->
                        new TransactionException(
                                ErrorConstants.INVALID_ERROR_CODE,
                                MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Status", "Valid Status are "+ Arrays.toString(Status.values()))));
    }
}
