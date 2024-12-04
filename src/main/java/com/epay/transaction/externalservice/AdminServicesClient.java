package com.epay.transaction.externalservice;


import com.epay.transaction.client.ApiClient;
import com.epay.transaction.dto.DowntimeAPIDto;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.*;

public class AdminServicesClient extends ApiClient {

    private static final LoggerUtility log = LoggerFactoryUtility.getLogger(AdminServicesClient.class);
    public static final String DOWNTIME_API_ENDPOINT = "/downtime/api";

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
        return downtimeList();
    }

   //TODO : Once Admin Service API ready need to remove below method.
    private List<DowntimeAPIDto> downtimeList(){
        List<DowntimeAPIDto>  list = new ArrayList<>();
        list.add(new DowntimeAPIDto("54","transaction api","NB","2023-03-10 00:51:08.745",
                "2023-03-13 00:51:26.833"));
        list.add(new DowntimeAPIDto("76","order-api","NB","2021-04-14 10:45:48",
                "2023-03-14 00:51:46.577"));
        return list;
    }
}
