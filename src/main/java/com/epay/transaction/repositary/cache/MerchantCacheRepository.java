package com.epay.transaction.repositary.cache;

import com.epay.transaction.dto.MerchantDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantCacheRepository {

    public Optional<MerchantDto> getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        //TODO : Call Hazlecast utility get method for Merchant by keys
        return Optional.empty();
    }
}
