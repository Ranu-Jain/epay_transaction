package com.epay.transaction.repositary.cache;

import com.epay.transaction.dto.MerchantDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MerchantCacheRepository {

    public Optional<MerchantDto> getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        //TODO : Call Hazelcast utility get method for Merchant by keys
        return Optional.empty();
    }

    public Optional<MerchantDto> getActiveMerchantByMID(String mID) {
        //TODO : Call Hazelcast utility get method for Merchant by MID
        return Optional.empty();
    }
}
