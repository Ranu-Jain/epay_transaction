package com.epay.transaction.repository;

import com.epay.transaction.entity.Transaction;
import com.epay.transaction.util.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Class Name:PaymentIntiation
 * *
 * Description:
 * *
 * Author:V1014352(Ranjan Kumar)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Optional<Transaction> findBySbiOrderRefNumberAndTransactionStatus(String sbiOrderRefNumber, TransactionStatus transactionStatus);
}
