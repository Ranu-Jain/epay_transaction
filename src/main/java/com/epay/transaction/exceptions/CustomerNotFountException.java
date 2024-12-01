package com.epay.transaction.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFountException extends RuntimeException {
    public CustomerNotFountException(String message) {
        super(message);
    }
}
