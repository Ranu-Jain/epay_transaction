package com.epay.transaction.validator;

import com.epay.transaction.dao.CustomerDao;
import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.TransactionConstant;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomerValidator extends BaseValidator {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(CustomerValidator.class);
    private final CustomerDao customerDao;

    public void validateCustomerRequest(CustomerDto customerDto) {
        logger.debug("Customer validation start for {}", customerDto);
        validateMandatoryFields(customerDto);
        logger.debug("Customer mandatory validation completed for {}", customerDto);
        validateFieldsValue(customerDto);
        logger.debug("Customer field validation completed for {}", customerDto);
        validateUniqueCustomer(customerDto);
        logger.debug("Customer all validation completed for {}", customerDto);
    }

    private void validateMandatoryFields(CustomerDto customerDto) {
        checkMandatoryField(customerDto.getName(), "Customer Name");
        checkMandatoryField(customerDto.getMId(), "MId");
        checkMandatoryFields("Email or Phone Number", customerDto.getEmail(), customerDto.getPhoneNumber());
        throwIfErrors();
    }

    private void validateFieldsValue(CustomerDto customerDto) {
        validateFieldLength(customerDto.getName(), TransactionConstant.NAME_LENGTH, "Customer Name");
        validateFieldWithRegex(customerDto.getEmail(), TransactionConstant.EMAIL_LENGTH, TransactionConstant.EMAIL_REGEX, "Email", "Please check Email Format or Max length");
        validateFieldWithRegex(customerDto.getPhoneNumber(), TransactionConstant.PHONE_REGEX, "Phone Number", "Please check Phone Number Format");
        validateFieldWithRegex(customerDto.getPinCode(), TransactionConstant.PIN_REGEX, "PinCode", "Please check PinCode Format");
        validateFieldWithRegex(customerDto.getGstIn(), TransactionConstant.GSTIN_REGEX, "GST Number", "Please check GST Format");
        throwIfErrors();
    }

    private void validateUniqueCustomer(CustomerDto customerDto) {
        boolean customerExists = customerDao.findCustomerByEmailPhoneAndMerchant(customerDto.getEmail(), customerDto.getPhoneNumber(), customerDto.getMId()).isPresent();
        if (customerExists) {
            addError("Customer", ErrorConstants.ALREADY_EXIST_ERROR_CODE, ErrorConstants.ALREADY_EXIST_ERROR_MESSAGE);
        }
        throwIfErrors();
    }
}


