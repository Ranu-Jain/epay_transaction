/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */


package com.epay.transaction.exceptionhandlers;


import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.dto.ErrorDto;
import com.epay.transaction.model.response.TransactionResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class TransactionExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({TransactionException.class, SQLException.class})
    public ResponseEntity<Object> handleSecurityException(TransactionException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage())
                .build();
        return generateResponseWithErrors(List.of(errorDto));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        if(CollectionUtils.isEmpty(ex.getErrorMessages())) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorCode(ex.getErrorCode())
                    .errorMessage(ex.getErrorMessage())
                    .build();
            return generateResponseWithErrors(List.of(errorDto));
        }
        return generateResponseWithErrors(ex.getErrorMessages());
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleJwtException(JwtException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in Authorization ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                TransactionResponse.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in handleGenericException ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(
                TransactionResponse.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();
        logger.error("Error in handleConflict ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                TransactionResponse.builder()
                        .status(1)
                        .errors(List.of(errorDto))
                        .build());
    }
    private ResponseEntity<Object> generateResponseWithErrors(List<ErrorDto> errors) {
        return ResponseEntity.ok().body(
                TransactionResponse.builder()
                        .status(1)
                        .errors(errors)
                        .build());
    }


}