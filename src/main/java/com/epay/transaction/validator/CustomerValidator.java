package com.epay.transaction.validator;

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.ErrorDto;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.util.ErrorConstants;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class CustomerValidator {
    // Constants

    public static final String CUSTOMER_NAME = "Customer Name";
    public static final String EMAIL = " email";
    public static final String MID = " Merchant ID";
    public static final String PHONE_NUMBER = "Phone Number";
    public static final String PINCODE = "Pincode";
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 255;
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(CustomerValidator.class);

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_REGEX = Pattern.compile("^\\d{10}$");
    private static final Pattern PIN_REGEX = Pattern.compile("^\\d{6}$");
    private static final Pattern GSTIN_REGEX = Pattern.compile("\\d{2}[A-Z]{5}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}");
    private static final Pattern ALLOWED_CHARACTERS_REGEX = Pattern.compile("^[A-Za-z0-9@#$*_\\-\\)\\(]+$");
    private final CustomerDao customerDao;
    List<ErrorDto> errorDtoList = new ArrayList<>();

    public CustomerValidator(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // Main validation method
    public void validateCustomerRequest(CustomerDto customerDto) {
        // Validate mandatory fields
        validateMandatoryFields(customerDto);
        // Use a field map and Java 8 Streams for field-specific validations
        Map<String, Runnable> fieldValidations = new HashMap<>();
        fieldValidations.put("name", () -> validateName(customerDto.getName()));
        fieldValidations.put("email", () -> validateEmail(customerDto.getEmail()));
        fieldValidations.put("phoneNumber", () -> validatePhoneNumber(customerDto.getPhoneNumber()));
        fieldValidations.put("gstin", () -> validateGstin(customerDto.getGstIn()));
        // Only validate PIN if it exists
//        if (!isNullOrEmpty(customerDto.getPinCode())) {
//            fieldValidations.put("pinCode", () -> validatePinCode(customerDto.getPinCode()));
//        }
//        fieldValidations.forEach((field, validation) -> Optional.ofNullable(field).ifPresent(key -> validation.run()));
        // TODO:Additional validations
//        if (!isNullOrEmpty(customerDto.getPinCode())) {
//            validatePinCodeCityState(customerDto.getPinCode(), customerDto.getCity(), customerDto.getState());
//        }
        validateUniqueCustomer(customerDto);
        //validateUniqueCustomerId(customerDto.getCustomerId(), customerDto.getMerchantId());
    }

    // Validate mandatory fields
    private void validateMandatoryFields(CustomerDto customerDto) {
        Optional.ofNullable(customerDto.getName()).orElseThrow(() -> new ValidationException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, CUSTOMER_NAME)));
        Optional.ofNullable(customerDto.getMId()).orElseThrow(() -> new ValidationException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, MID)));
        if (isNullOrEmpty(customerDto.getEmail()) && isNullOrEmpty(customerDto.getPhoneNumber())) {
            throw new ValidationException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Phone number or email"));
        }
    }

    // Validation methods for individual fields
    private void validateName(String name) {
        if (!isNullOrEmpty(name)) {
            validateNameLength(name);
            validateAllowedCharacters("name", name);
        }
    }

    private void validateEmail(String email) {
        if (!isNullOrEmpty(email)) {
            validateEmailFormat(email);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!isNullOrEmpty(phoneNumber)) {
            validatePhoneNumberFormat(phoneNumber);
        }
    }

    private void validateGstin(String gstin) {
        if (!isNullOrEmpty(gstin)) {
            validateGstinFormat(gstin);
            validateGstinLength(gstin);
        }
    }

    private void validatePinCode(String pinCode) {
        validatePinCodeFormat(pinCode);
        validatePinCodeLength(pinCode);
    }

    // Field-specific validation logic
    private void validateNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, CUSTOMER_NAME));
        }
    }

    private void validateAllowedCharacters(String fieldName, String value) {
        if (!ALLOWED_CHARACTERS_REGEX.matcher(value).matches()) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Special Character"));
        }
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EMAIL));
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, EMAIL));
        }
    }

    private void validatePhoneNumberFormat(String phoneNumber) {
        if (!PHONE_REGEX.matcher(phoneNumber).matches()) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, PHONE_NUMBER));
        }
    }

    private void validateGstinFormat(String gstin) {
        if (!GSTIN_REGEX.matcher(gstin).matches()) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "gstIn"));
        }
    }

    private void validateGstinLength(String gstin) {
        if (gstin.length() != 15) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "gstin"));
        }
    }

    private void validatePinCodeFormat(String pinCode) {
        if (!PIN_REGEX.matcher(pinCode).matches()) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "PIN"));
        }
    }

    private void validatePinCodeLength(String pinCode) {
        if (pinCode.length() != 6) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "PIN"));
        }
    }

    // Validate PIN code against city and state
//    private void validatePinCodeCityState(String pinCode, String city, String state) {
//        String cityState = PIN_CITY_STATE_MAP.get(pinCode);
//        if (cityState == null || !cityState.equalsIgnoreCase(city + "," + state)) {
//            throw new ValidationException("PIN code does not match the given city and state.");
//        }
//    }
    // Business validation: unique customer check
    private void validateUniqueCustomer(CustomerDto customerDto) {
        Optional<CustomerDto> existingCustomer = customerDao.findCustomerByEmailPhoneAndMerchant(
                customerDto.getEmail(),
                customerDto.getPhoneNumber()
        );
        // If the existing customer is present, perform validation
        existingCustomer.ifPresent(existingCustomerDto -> {
            // Check if merchantId and name are both not null before calling .equals()
            boolean isSameMerchantAndName =
                    customerDto.getMId() != null &&
                            customerDto.getMId().equals(existingCustomerDto.getMId()) &&
                            customerDto.getName() != null &&
                            customerDto.getName().equals(existingCustomerDto.getName());

            // If the conditions are met, throw a validation exception
            if (isSameMerchantAndName) {
                throw new ValidationException(ErrorConstants.ALREADY_EXIST_ERROR_CODE, MessageFormat.format(ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE, CUSTOMER_NAME));
            }
        });
    }

    // Business validation: unique customer ID check
//    private void validateUniqueCustomerId(String customerId, String merchantId) {
//        if (customerDao.existsByCustomerIdAndMerchantId(customerId, merchantId)) {
//            throw new ValidationException("Customer ID is already used for this merchant.");
//        }
//    }
    // Utility: check if a string is null or empty
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}

