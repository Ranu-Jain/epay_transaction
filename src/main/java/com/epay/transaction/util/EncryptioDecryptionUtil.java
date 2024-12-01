package com.epay.transaction.util;

import com.epay.transaction.dto.CustomerDto;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.CustomerRequest;
import com.epay.transaction.model.response.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.encryptdecrypt.util.EncryptionDecryptionAlgo;
import com.sbi.epay.encryptdecrypt.util.GCMIvLength;
import com.sbi.epay.encryptdecrypt.util.GCMTagLength;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Class Name:EncryptioDecryptionUtil
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
public class EncryptioDecryptionUtil {
    private static final LoggerUtility logger=LoggerFactoryUtility.getLogger(EncryptioDecryptionUtil.class);

    public static CustomerDto buildCustomerDTO(CustomerRequest customerRequest, MerchantDto merchantDto) {
        try {
            ObjectMapper objectMapper =new ObjectMapper() ;
            DecryptionService decryptionService = new DecryptionService();
            SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(),merchantDto.getKek(),merchantDto.getAek(), EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            byte[] str= Base64.getDecoder().decode(customerRequest.getCustomerRequest());
            String decryptedCustomerRequest = decryptionService.decryptValue(str,secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            return objectMapper.readValue(decryptedCustomerRequest, CustomerDto.class);
        } catch (JsonProcessingException e) {
            logger.error("error in buildCustomerDTO  ", e);
            throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE,ErrorConstants.INVALID_ERROR_MESSAGE);
        }
    }
    public static String buildEncryptCustomerResponse(CustomerDto customerDto, MerchantDto merchantDto) {
        String customerResponseString = null;
        EncryptionService encryptionService=new EncryptionService();
        ObjectMapper objectMapper =new ObjectMapper() ;

        try {
            customerResponseString = objectMapper.writeValueAsString(customerDto);
            SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(),merchantDto.getKek(),merchantDto.getAek(),EncryptionDecryptionAlgo.AES_GCM_NO_PADDING,GCMIvLength.MAXIMUM,GCMTagLength.STANDARD);
            byte[] encryptedByte= encryptionService.encryptValue(secretKey,customerResponseString, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);

            return Base64.getEncoder().encodeToString(encryptedByte);

        } catch (JsonProcessingException e) {
            throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE,"json"));
        }

    }

    /**
     * Build Decrypt Response
     *
     * @param getRequest as String
     * @param merchantDto as Object
     * @return Object of merchantDto.
     */
    public static String buildDecryptResponse(String getRequest, MerchantDto merchantDto) {
            DecryptionService decryptionService = new DecryptionService();
            SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(),merchantDto.getKek(),merchantDto.getAek(), EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            byte[] str= Base64.getDecoder().decode(getRequest);
            String decryptedCustomerRequest = decryptionService.decryptValue(str,secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            return decryptedCustomerRequest;

    }

    /**
     *Build Encrypt Response
     *
     * @param getRequest as String
     * @param merchantDto as Object
     * @return Object of merchantDto.
     */
    public static String buildEncryptResponse(String getRequest, MerchantDto merchantDto) {
           EncryptionService encryptionService=new EncryptionService();
            SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(),merchantDto.getKek(),merchantDto.getAek(),EncryptionDecryptionAlgo.AES_GCM_NO_PADDING,GCMIvLength.MAXIMUM,GCMTagLength.STANDARD);
            byte[] encryptedByte= encryptionService.encryptValue(secretKey,getRequest, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            return Base64.getEncoder().encodeToString(encryptedByte);

    }


    /**
     * Encrypt order Request
     *
     * @param orderResponse as Object
     * @param merchantDto as Object
     * @return Object of merchantDto.
     */

    public static String buildEncryptOrderResponse(OrderResponse orderResponse, MerchantDto merchantDto) {
        String customerResponseString = null;
        EncryptionService encryptionService=new EncryptionService();
        ObjectMapper objectMapper =new ObjectMapper() ;

        try {
            customerResponseString = objectMapper.writeValueAsString(orderResponse);
            SecretKey secretKey = KeyProviderService.getDecryptedMEK(merchantDto.getMek(),merchantDto.getKek(),merchantDto.getAek(),EncryptionDecryptionAlgo.AES_GCM_NO_PADDING,GCMIvLength.MAXIMUM,GCMTagLength.STANDARD);
            byte[] encryptedByte= encryptionService.encryptValue(secretKey,customerResponseString, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);

            return Base64.getEncoder().encodeToString(encryptedByte);

        } catch (JsonProcessingException e) {
            throw new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, ErrorConstants.NOT_FOUND_ERROR_CODE);
        }

    }
}
