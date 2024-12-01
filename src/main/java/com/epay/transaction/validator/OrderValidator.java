package com.epay.transaction.validator;


import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.entity.AggMerchantRfcRuleConfig;
import com.epay.transaction.exceptions.ValidationException;
import com.epay.transaction.repositary.AggMerchantRfcRuleConfigRepo;
import com.epay.transaction.util.ErrorConstants;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class OrderValidator {



    private final AggMerchantRfcRuleConfigRepo aggMerchantRfcRuleConfigRepo;

    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(OrderValidator.class);
    public void validateOrderRequest(OrderDto orderDto) {
        logger.info("ClassName - OrderValidator,MethodName - validateOrderRequest, Validate Order request object Started");
        validateOrderAmount(orderDto.getMid(),orderDto.getAmount());
    }

    public void validateOrderAmount(String mid,Double amount) {
        Optional<AggMerchantRfcRuleConfig> merchantRulConfig = aggMerchantRfcRuleConfigRepo.findByMerchantId(mid);

        logger.info("get merchant amount :"+amount +",:"+merchantRulConfig.get().getThresholdAmount());
       if ((merchantRulConfig.get().getThresholdAmount()<=amount)||amount<0) {
            throw new ValidationException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "Order Amount"));
        }
    }




}
