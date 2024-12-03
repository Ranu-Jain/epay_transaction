package com.epay.transaction.externalservice;


import com.epay.transaction.client.ApiClient;
import com.epay.transaction.dto.DowntimeAPIDto;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class AdminServicesClient extends ApiClient {
    public static final String DOWNTIME_API_ENDPOINT = "/downtime/api";
    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(AdminServicesClient.class);

    public AdminServicesClient(String baseUrl) {
        super(baseUrl);
    }

    public List<DowntimeAPIDto> getDowntimeAPIs() {
        log.info(" call Downtime API starts ");
        HttpHeaders headers = prepareHttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI uri = URI.create(getBaseUrl() + DOWNTIME_API_ENDPOINT);
        //TODO : Once Admin Service API ready need to use below method
        //ResponseEntity<DowntimeAPIDto> entity = getRestTemplate().exchange(uri, HttpMethod.GET, request, DowntimeAPIDto.class);
        //return entity.getBody();
        log.info(" call Downtime API ends ");
        return Collections.emptyList();
    }
}
