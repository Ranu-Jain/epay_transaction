package com.epay.transaction.externalservice;


import com.epay.transaction.client.ApiClient;
import com.epay.transaction.dto.MerchantDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;

public class MerchantServicesClient extends ApiClient {

    public static final String MERCHANT_ENDPOINT = "/";

    public MerchantServicesClient(String baseUrl) {
        super(baseUrl);
    }

    public MerchantDto getMerchantInfo(String apiKey, String secretKey) {
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
                .accessTokenExpiryTime(2l)
                .status("active")
                .build();
    }
}
