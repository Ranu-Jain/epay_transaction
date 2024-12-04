package com.epay.transaction.service;


import com.epay.transaction.dao.PaymentInitiationDao;
import com.epay.transaction.dto.TransactionDto;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.model.response.TransactionResponse;
import com.epay.transaction.util.EPayIdentityUtil;
import com.epay.transaction.util.UniqueIDGenerator;
import com.epay.transaction.util.enums.TransactionStatus;
import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.logging.utility.LoggerFactoryUtility;
import com.sbi.epay.logging.utility.LoggerUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentInitiationService {
    private static final LoggerUtility logger = LoggerFactoryUtility.getLogger(PaymentInitiationService.class);
    private final PaymentInitiationDao paymentInitiationDao;
    private final UniqueIDGenerator uniqueIDGenerator;

    public TransactionResponse<PaymentResponse> initiatePayment(String payMode, PaymentRequest paymentRequest) {
        logger.info("Payment Initiation Request for payMode {} , paymentRequest {}", payMode, paymentRequest);
        EPayPrincipal ePayPrincipal = EPayIdentityUtil.getUserPrincipal();

        // Step 1: Validated Merchant Details
        paymentInitiationDao.getActiveMerchantByMID(ePayPrincipal.getMid());

        // Step 2: Validated Order Details
        paymentInitiationDao.getValidOrder(ePayPrincipal.getOrderRef());

        // Step 3: Fetch Transaction
        TransactionDto transactionDto = paymentInitiationDao.getValidTransaction(ePayPrincipal.getOrderRef());

        // Step 4: Validated Transaction
        //TODO : Need to add the PaymentInitiationValidator

        // Step 5: Generate 15-digit ATRN Number and update Transaction Object
        transactionDto.setAtrnNumber(uniqueIDGenerator.generateATRNNumber());
        transactionDto.setStatus(TransactionStatus.PAYMENT_INITATED);
        paymentInitiationDao.saveTransaction(transactionDto);

        // Step 6: Call Payment Service
        PaymentResponse paymentResponse = paymentInitiationDao.initiatePayment(payMode, paymentRequest);
        logger.info("Payment Initiation completed for payMode {} , paymentResponse {}", payMode, paymentResponse);

        // Return the response
        return TransactionResponse.<PaymentResponse>builder().status(1).count(1L).data(List.of(paymentResponse)).build();
    }
}