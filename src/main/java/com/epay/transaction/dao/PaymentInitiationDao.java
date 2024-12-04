package com.epay.transaction.dao;

import com.epay.transaction.dto.MerchantDto;
import com.epay.transaction.dto.OrderDto;
import com.epay.transaction.dto.TransactionDto;
import com.epay.transaction.externalservice.PaymentServicesClient;
import com.epay.transaction.model.request.PaymentRequest;
import com.epay.transaction.model.response.PaymentResponse;
import com.epay.transaction.util.enums.PayMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PaymentInitiationDao {

    private final OrderDao orderDao;
    private final TransactionDao transactionDao;
    private final MerchantDao merchantDao;
    private final PaymentServicesClient paymentServicesClient;

    public MerchantDto getActiveMerchantByMID(String mID) {
        return merchantDao.getActiveMerchantByMID(mID);
    }

    public OrderDto getValidOrder(String orderRefNumber) {
        return orderDao.getValidOrderBySBIOrderRefNumber(orderRefNumber);
    }

    public TransactionDto getValidTransaction(String orderRefNumber) {
        return transactionDao.getValidTransaction(orderRefNumber);
    }

    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        return transactionDao.saveTransaction(transactionDto);
    }

    public PaymentResponse initiatePayment(String payMode, PaymentRequest paymentRequest){
        return paymentServicesClient.initiatePayment(PayMode.getPayMode(payMode), paymentRequest);
    }
}
