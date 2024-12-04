package com.epay.transaction.dao;

import com.epay.transaction.dto.TransactionDto;
import com.epay.transaction.entity.Transaction;
import com.epay.transaction.exceptions.TransactionException;
import com.epay.transaction.repository.TransactionRepository;
import com.epay.transaction.util.ErrorConstants;
import com.epay.transaction.util.enums.TransactionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
public class TransactionDao {

    private final TransactionRepository transactionRepository;
    private final ObjectMapper objectMapper;

    public TransactionDto getValidTransaction(String orderRefNumber) {
        Transaction transaction = transactionRepository.findBySbiOrderRefNumberAndTransactionStatus(orderRefNumber, TransactionStatus.BOOKED)
                .orElseThrow(() -> new TransactionException(ErrorConstants.NOT_FOUND_ERROR_CODE, MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Transaction")));
        return objectMapper.convertValue(transaction, TransactionDto.class);
    }

    public TransactionDto saveTransaction(TransactionDto transactionDto){
        Transaction transaction = objectMapper.convertValue(transactionDto, Transaction.class);
        transactionRepository.save(transaction);
        return objectMapper.convertValue(transaction, TransactionDto.class);
    }
}
