package com.epay.transaction.util.enums;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.util.ErrorConstants;

import java.text.MessageFormat;
import java.util.Arrays;

public enum PayMode {
    UPI, INB, CARDS, O_INB;

    public static PayMode getPayMode(String payMode) {
        return Arrays.stream(values()).filter(p -> p.name().equalsIgnoreCase(payMode)).findFirst().orElseThrow(() -> new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "PayMode", "Valid PayMode are " + Arrays.toString(PayMode.values()))));
    }
}
