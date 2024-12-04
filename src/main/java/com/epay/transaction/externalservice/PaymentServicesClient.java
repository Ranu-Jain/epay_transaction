package com.epay.transaction.externalservice;


import com.epay.transaction.client.ApiClient;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.enums.PayMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Arrays;

public class PaymentServicesClient extends ApiClient {

    public static final String INB_API_ENDPOINT = "/sbi/inb/initiate";

    public PaymentServicesClient(String baseUrl) {
        super(baseUrl);
    }

    public PaymentResponse initiatePayment(PayMode payMode, PaymentRequest paymentRequest) {
        return switch (payMode) {
            case INB -> initiateSBIINBPayments(paymentRequest);
            default ->
                    throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Paymode", "Valid Paymode are " + Arrays.toString(PayMode.values())));
        };
    }

    public PaymentResponse initiateSBIINBPayments(PaymentRequest paymentRequest) {
        HttpHeaders headers = prepareHttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PaymentRequest> httpRequest = new HttpEntity<>(paymentRequest, headers);
        URI uri = URI.create(getBaseUrl() + INB_API_ENDPOINT);
        //TODO : Once Payment Service API ready need to use below method
        //ResponseEntity<PaymentResponse> entity = getRestTemplate().exchange(uri, HttpMethod.POST, request, PaymentResponse.class);
        //return entity.getBody();
        return PaymentResponse.builder().paymentResponse("Payment Done").build();
    }

}
