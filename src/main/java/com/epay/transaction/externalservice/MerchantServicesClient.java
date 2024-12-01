package com.epay.transaction.externalservice;


import com.epay.transaction.client.ApiClient;
import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.service.TokenService;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;

public class MerchantServicesClient extends ApiClient {
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(TokenService.class);

    public static final String MERCHANT_ENDPOINT = "/";

    public MerchantServicesClient(String baseUrl) {
        super(baseUrl);
    }

    public MerchantDto getMerchantInfo(String apiKey, String secretKey) {
        log.info(" call Merchant API starts ");
        HttpHeaders headers = prepareHttpHeaders();
        headers.add("Merchant-API-Key", apiKey);
        headers.add("Merchant-Secret-Key", secretKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI uri = URI.create(getBaseUrl() + MERCHANT_ENDPOINT);
        //TODO : Once Merchant Service API ready need to use below method
        //ResponseEntity<MerchantDto> entity = getRestTemplate().exchange(uri, HttpMethod.POST, request, MerchantDto.class);
        //return entity.getBody();
        log.info(" call Merchant API ends ");
        return MerchantDto.builder()
                .mID(apiKey)
                .accessTokenExpiryTime(2)
                .status("active")
                .apiKey(apiKey)
                .secretKey(secretKey)
                .build();
    }
    public MerchantDto getMerchantByKeys(String apiKey, String secretKey) {
        HttpHeaders headers = prepareHttpHeaders();
        headers.add("Merchant-API-Key", apiKey);
        headers.add("Merchant-Secret-Key", secretKey);
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI uri = URI.create(getBaseUrl() + MERCHANT_ENDPOINT);
        //TODO : Once Merchant Service API ready need to use below method
        //ResponseEntity<MerchantDto> entity = getRestTemplate().exchange(uri, HttpMethod.POST, request, MerchantDto.class);
        //return entity.getBody();
        return MerchantDto.builder()
                .mID("1122323")
                .accessTokenExpiryTime(2)
                .status("active")
                .build();
    }
    public MerchantDto getActiveMerchantByMID(String mid) {
        HttpHeaders headers = prepareHttpHeaders();
        //TODO : need to set the Merchant Token
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI uri = URI.create(getBaseUrl() + MERCHANT_ENDPOINT+mid);
        //TODO : Once Merchant Service API ready need to use below method for getting the active merchant by MID by passing MID and Status as Active
        //ResponseEntity<MerchantDto> entity = getRestTemplate().exchange(uri, HttpMethod.POST, request, MerchantDto.class);
        //return entity.getBody();
        return MerchantDto.builder()
                .mID(mid)
                .transactionTokenExpiryTime(2)
                .status("active")
                .build();
    }
    public MerchantDto getMerchantByMID(String mId) {
        HttpHeaders headers = prepareHttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI uri = URI.create(getBaseUrl() + MERCHANT_ENDPOINT + mId);
        //TODO : Once Merchant Service API ready need to use below method
        //ResponseEntity<MerchantDto> entity = getRestTemplate().exchange(uri, HttpMethod.POST, request, MerchantDto.class);
        //return entity.getBody();
        /*return MerchantDto.builder()
                .mID("1122323")
                .accessTokenExpiryTime(Long.valueOf("1731579747533"))
                .status("active")
                .mek("VFm7kkd0m38jAX2TmKJeFbUWlM7JVWsa40ruIic4BunFDn4sdW52vMk5pW1Vj4+Nl2HkVI4mmS6hEBLs")
                .kek("V1pNLDs/WfX/7PF72xTXzeiNylWPkeeXXTU/krS7OoYukWYHzGv/JOlx8UK3Ly7iw2ygGoOp6BY9XwZP")
                .aek("BiIZ5feKr16Td3XSpVywqXlwRNfSy9Gtis04WqEbD/0=")
                .build();*/
        /*return MerchantDto.builder()
                .mID("1122323")
                .status("active")
                .mek("rjQ3kd14SXrwxuJ4yeM2BpEsBJFl1cVrjsd4TQmhehRnDH6/EZ8P61ar5dcrLAD6WOVl07nWcfJEUhCP")
                .aek("BiIZ5feKr16Td3XSpVywqXlwRNfSy9Gtis04WqEbD/0")
                .kek("PdG6/5BBVb5ZeBnM45J+xO0H9zjGoQQ9eUkbqQ5/XC9rcu286py5pMq8xtSxX16zj5Vu2qwI120V3eKT")
                .build();
            */
        return MerchantDto.builder()
                .mID(mId)
                .status("active")
                .mek("RX6rTMyBwIwdWvYQwB5CuEewO6WmFj1chO19h87YA/KVMn/yMAO5zkWxNnhOMYzC")
                .aek("6XTCPyn+bZJskeENfY5boA==")
                .kek("E8QLFgWlfUgPZDNF57mC71ObRfARkj9chPgCKW0BjDCyoYzZfj5f0eI0DAYobYry")
                .build();
    }
}
