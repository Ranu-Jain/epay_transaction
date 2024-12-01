package com.epay.transaction;

import com.sbi.epay.authentication.service.JwtService;
import com.sbi.epay.encryptdecrypt.service.DecryptionService;
import com.sbi.epay.encryptdecrypt.service.EncryptionService;
import com.sbi.epay.encryptdecrypt.service.KeyGeneratorService;
import com.sbi.epay.encryptdecrypt.service.KeyProviderService;
import com.sbi.epay.encryptdecrypt.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Class Name:TestJson
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
public class TestJson {
    public static void main(String[] args) throws Exception {
        try {
            EncryptionService encryptionService = new EncryptionService();
            DecryptionService decryptionService = new DecryptionService();
            KeyGeneratorService keyGeneratorService = new KeyGeneratorService();

            //Step 1 : Key generator
            String aek = "r4YJLpWvRkDujmWMgl5FlQ==";
            System.out.println("aek: " + aek);
            String kek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("kek: " + kek);
            String mek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("mek: " + mek);

            //Step 2 : Key Encryption
            String kekbytes = encryptionService.encryptSecretKey(kek, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("kekbytes : " + kekbytes);
            String deKek=DecryptionService.decryptKey(kekbytes,aek,EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("Decripted KEK"+deKek);
            System.out.println("Original KEK"+kek);
            String mekbytes = encryptionService.encryptSecretKey(mek, kek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("mekbytes : " + mekbytes);

            //Step 3 : Key Provider

            KeyProviderService keyProviderService = new KeyProviderService();
            SecretKey secretKey = keyProviderService.getDecryptedMEK(mekbytes, kekbytes, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("secret: "+secretKey);
            System.out.println("string Secret key: "+secretKey.getEncoded());

            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
           // SecretKey s1=Base64.getDecoder().decode(encodedKey.)
            System.out.println("encodedKey: "+encodedKey);
            // Step 4 Encryption Decryption
            String json = "{\"name\":\"ranjan\",\"phoneNumber\":\"9818809058\",\"email\":\"_ran@gmail.com\",\"gstIn\":\"27AAACS8577K2ZO\",\"mid\":\"12345\"}";
            byte[] bytes = encryptionService.encryptValue(secretKey, json, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("encripted:="+ bytes.toString());
            String str= Base64.getEncoder().encodeToString(bytes);
            System.out.println("str:="+ str);
            byte[] decode=Base64.getDecoder().decode(str);
            String decryptionValue = decryptionService.decryptValue(decode, secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println(decryptionValue);
        } catch (NullPointerException e) {
            throw new Exception(e.getMessage() ,e);
        }

    }
}
