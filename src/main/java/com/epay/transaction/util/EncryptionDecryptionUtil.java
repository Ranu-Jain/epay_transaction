package com.epay.transaction.util;

import com.epay.transaction.dto.MerchantDto;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.HashingService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import com.sbi.epay.encryptdecrypt.util.HashAlgorithm;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Class Name:EncryptionDecryptionUtil
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
@RequiredArgsConstructor
@Component
public class EncryptionDecryptionUtil {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(EncryptionDecryptionUtil.class);
    private final EncryptionService encryptionService;
    private final DecryptionService decryptionService;

    public String decryptRequest(String request, MerchantDto merchantDto) {
        SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek(), EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
        return decryptionService.decryptValue(Base64.getDecoder().decode(request), secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
    }

    public String encryptRequest(String request, MerchantDto merchantDto) {
        SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(), merchantDto.getKek(), merchantDto.getAek(), EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
        byte[] encryptedValue = encryptionService.encryptValue(secretKey, request, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String hashValue(String... values){
        String value = String.join("", values);
        byte[] bytes = HashingService.generateHash(Base64.getDecoder().decode(value), HashAlgorithm.SHA_512);
        return Base64.getEncoder().encodeToString(bytes);
    }

}
