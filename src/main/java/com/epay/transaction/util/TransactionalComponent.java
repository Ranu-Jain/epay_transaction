package com.epay.transaction.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TransactionalComponent {

	@Value("${merchanturl}")
    private String merchantDetailsUrl;
	
	@Value("${validateMerchantService}")
    private String validateMerchantServiceUrl;
	
    @Value("${paymenturl}")
    private String paymentUrl;
}





