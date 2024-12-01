package com.epay.transaction.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

}
