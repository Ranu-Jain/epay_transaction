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
            String aek = "6XTCPyn+bZJskeENfY5boA==";
            System.out.println("aek: " + aek);
            String kek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("kek: " + kek);
            String mek = keyGeneratorService.generateKeyByAlgo(SecretKeyLength.AES_128, KeyGenerationAlgo.AES);
            System.out.println("mek: " + mek);

            //Step 2 : Key Encryption
            String kekbytes ="E8QLFgWlfUgPZDNF57mC71ObRfARkj9chPgCKW0BjDCyoYzZfj5f0eI0DAYobYry"; // encryptionService.encryptSecretKey(kek, aek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("kekbytes : " + kekbytes);
            String deKek=DecryptionService.decryptKey(kekbytes,aek,EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("Decripted KEK"+deKek);
            System.out.println("Original KEK"+kek);
            String mekbytes = "RX6rTMyBwIwdWvYQwB5CuEewO6WmFj1chO19h87YA/KVMn/yMAO5zkWxNnhOMYzC";//encryptionService.encryptSecretKey(mek, kek, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
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
            System.out.println("encripted json request:="+ bytes.toString());
            String str= Base64.getEncoder().encodeToString(bytes);
            System.out.println("str:="+ str);
            byte[] decode=Base64.getDecoder().decode("SAzfCs5jx/h5BEl8O5LI71EWNb9+ZH1ZqzvBNi5qHlpZpfeQviQpqvSY9BXWwLSjcMZMJziFYgcINTdhLPm4xFVA/b3A4ID42TB7rIu1XZRQwOasysPpYEnOkV1dFlocZHyw4CMUfH1a9CZSyqZPDufWuSXpYi+LAQYdhZi7T9d6zXUA214GyR5+n7E54k9SAJ0oQnxh2+c4JNE0z3LqNhZMKLYx1+gGaR6HqfbYpNjjxTY2trOJ8HJv1DbEi5ajYg+xWeyy6cfsYyHUn5b9dUFqbEUBwYa4Yj47Fl5uN9BOjb86947b/GJGiN/V/BscHD2A3J19D5eNlt0xrmKrnDmagBJ6FQ3tLQ4tdehKIJDlvRkvlVGGk67ozzYyMUmnpztXM2eXdu8vc7M1ArOdeEtGn7ve0dIRGHnQZ6NUqLg6JNmk2lFVrMkwhO5NGk9F7ROdYRClRAKF9A2GPDbIZMMQN7s6kkf2MuibXa676/Q5LDEUJ96dd+Jysngn1TslXYgxKxe/UGTD1reJK27cL7T8hx52Iknaey9iuhi8dVsSEjmPYQy1L7IPh6Q37w==");
            String decryptionValue = decryptionService.decryptValue(decode, secretKey, EncryptionDecryptionAlgo.AES_GCM_NO_PADDING, GCMIvLength.MAXIMUM, GCMTagLength.STANDARD);
            System.out.println("decrepted respnse:"+decryptionValue);
        } catch (NullPointerException e) {
            throw new Exception(e.getMessage() ,e);
        }

    }
}
