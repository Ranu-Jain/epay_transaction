package com.epay.transaction.validator;

import com.epay.transaction.dto.ErrorDto;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.util.ErrorConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class Name:BaseValidator
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
public class BaseValidator {

    List<ErrorDto> errorDtoList = new ArrayList<>();

    void checkMandatoryField(String value, String fieldName) {
        if (StringUtils.isEmpty(value)) {
            addError(fieldName, ErrorConstants.MANDATORY_FOUND_ERROR_CODE, ErrorConstants.MANDATORY_ERROR_MESSAGE);
        }
    }

    void checkMandatoryFields(String fieldName, String... values) {
        boolean allEmpty = Arrays.stream(values).allMatch(StringUtils::isEmpty);
        if (allEmpty) {
            addError(fieldName, ErrorConstants.MANDATORY_FOUND_ERROR_CODE, ErrorConstants.MANDATORY_ERROR_MESSAGE);
        }
    }

    void validateFieldLength(String value, int maxLength, String fieldName) {
        if (StringUtils.isNotEmpty(value) && value.length() > maxLength) {
            addError(fieldName, ErrorConstants.INVALID_ERROR_CODE, "Max allowed length is " + maxLength);
        }
    }

    void validateFieldWithRegex(String value, int maxLength, String regex, String fieldName, String message) {
        if (StringUtils.isNotEmpty(value) && (value.length() > maxLength || validate(value, regex))) {
            addError(fieldName, ErrorConstants.INVALID_ERROR_CODE, message + " " + maxLength);
        }
    }

    void validateFieldWithRegex(String value, String regex, String fieldName, String message) {
        if (StringUtils.isNotEmpty(value) && validate(value, regex)) {
            addError(fieldName, ErrorConstants.INVALID_ERROR_CODE, message);
        }
    }

    void addError(String fieldName, String errorCode, String errorMessage) {
        errorDtoList.add(ErrorDto.builder().errorCode(errorCode).errorMessage(MessageFormat.format(errorMessage, fieldName)).build());
    }

    void throwIfErrors() {
        if (!errorDtoList.isEmpty()) {
            throw new ValidationException(new ArrayList<>(errorDtoList));
        }
    }

    boolean validate(String value, String regex) {
        return !Pattern.matches(regex, value);
    }
}
