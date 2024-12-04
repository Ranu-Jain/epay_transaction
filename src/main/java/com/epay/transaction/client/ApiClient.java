package com.epay.transaction.client;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

public class ApiClient {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ApiResponseErrorHandler());
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected HttpHeaders prepareHttpHeaders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = StringUtils.EMPTY;
        if(ObjectUtils.isNotEmpty(authentication)){
            token = (String) authentication.getCredentials();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.add("X-CorrelationId", MDC.get("correlation"));
        return headers;
    }


}