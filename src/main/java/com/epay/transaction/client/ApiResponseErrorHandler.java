package com.epay.transaction.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class ApiResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
      String errorMessage;

        try {
            errorMessage = IOUtils.toString(response.getBody(), UTF_8);
        } catch (Exception e) {
            log.error("unable to parse error response", e);
            errorMessage = e.getMessage();
        }

        log.error(errorMessage);
        throw new ApiException(response.getStatusCode().value(), errorMessage);
   }
}
