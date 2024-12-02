package com.epay.transaction.validator;


import com.epay.transaction.dto.OrderDto;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(OrderValidator.class);

    public void validateOrderRequest(OrderDto orderDto) {
        logger.info("validateOrderRequest, Validate Order request object Started");
        // TODO : Need to add the order creation validation
    }

}
