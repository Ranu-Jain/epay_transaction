package com.epay.transaction.model.request;

import com.epay.transaction.util.enums.OperatingMode;
import lombok.Data;

@Data
public class PaymentRequest {
    private OperatingMode operatingMode;
}
