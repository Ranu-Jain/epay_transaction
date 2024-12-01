package com.epay.transaction.config;

import com.epay.transaction.externalservice.MerchantServicesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {

    @Value("${external.api.merchant.services.base.path}")
    private String merchantServicesBasePath;

    @Bean
    public MerchantServicesClient constructMerchantServicesClient() {
        return new MerchantServicesClient(merchantServicesBasePath);
    }

}