package com.epay.transaction.exceptions;

import lombok.Data;


public class CustomerException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public CustomerException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
