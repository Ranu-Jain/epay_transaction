package com.epay.transaction.config;

import com.epay.transaction.externalservice.AdminServicesClient;
import com.epay.transaction.externalservice.MerchantServicesClient;
import com.epay.transaction.externalservice.PaymentServicesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class APIConfig {

    @Value("${external.api.merchant.services.base.path}")
    private final String merchantServicesBasePath;

    @Value("$external.api.admin.services.base.path")
    private final String adminServicesBasePath;

    @Value("$external.api.payment.services.base.path")
    private final String paymentServicesBasePath;

    @Bean
    public MerchantServicesClient constructMerchantServicesClient() {
        return new MerchantServicesClient(merchantServicesBasePath);
    }

    @Bean
    public AdminServicesClient constructAdminServicesClient() {
        return new AdminServicesClient(adminServicesBasePath);
    }

    @Bean
    public PaymentServicesClient constructPaymentServicesClient() {
        return new PaymentServicesClient(paymentServicesBasePath);
    }


}