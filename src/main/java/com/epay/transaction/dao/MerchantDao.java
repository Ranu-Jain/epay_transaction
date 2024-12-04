package com.epay.transaction.dao;

import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.repository.cache.MerchantCacheRepo;
import com.epay.transaction.util.ErrorConstants;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MerchantDao {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(MerchantDao.class);

    private final MerchantCacheRepo merchantCacheRepository;
    private final MerchantServicesClient merchantServicesClient;

    public MerchantDto getActiveMerchantByKeys(String merchantApiKey, String merchantSecretKey) {
        log.info(" getActiveMerchantByKeys starts");
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByKeys(merchantApiKey, merchantSecretKey);
        if (merchantDto.isEmpty()) {
            merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantInfo(merchantApiKey, merchantSecretKey));
        }
        log.info(" getActiveMerchantByKeys ends");
        return merchantDto.orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
    }

    public MerchantDto getActiveMerchantByMID(String mID) {
        Optional<MerchantDto> merchantDto = merchantCacheRepository.getActiveMerchantByMID(mID);
        if (merchantDto.isEmpty()) {
            merchantDto = Optional.ofNullable(merchantServicesClient.getMerchantByMID(mID));
        }
        return merchantDto.orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Valid Merchant")));
    }
}
