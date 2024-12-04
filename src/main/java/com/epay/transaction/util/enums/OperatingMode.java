package com.epay.transaction.util.enums;

import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.util.ErrorConstants;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;

@Getter
public enum OperatingMode {
    DOM("Domestic"), INTL ("International");

    private final String label;
    OperatingMode(String label) {
        this.label = label;
    }

    public static OperatingMode getOperatingMode(String label) {
        return Arrays.stream(values()).filter(p -> p.getLabel().equalsIgnoreCase(label)).findFirst().orElseThrow(() -> new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "OperatingMode", "Valid OperatingMode are " + Arrays.toString(OperatingMode.values()))));
    }
}
