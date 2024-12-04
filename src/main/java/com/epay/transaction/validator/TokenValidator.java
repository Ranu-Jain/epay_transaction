package com.epay.transaction.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
 * Copyright (c) [2024] [State Bank of India]
 * All rights reserved.
 *
 * @author Shilpa Kothre
 */
@Component
@RequiredArgsConstructor
public class TokenValidator extends BaseValidator {

    public void validateAccessTokenRequest(String merchantApiKeyId, String merchantApiKeySecret) {
        checkMandatoryField("Merchant Api Key Id", merchantApiKeyId);
        checkMandatoryField("Merchant Api Key Secret", merchantApiKeySecret);
        throwIfErrors();
    }

}
