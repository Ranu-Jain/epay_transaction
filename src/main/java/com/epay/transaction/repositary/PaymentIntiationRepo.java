package com.epay.transaction.repositary;

import com.epay.transaction.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface PaymentIntiationRepo extends JpaRepository<PaymentTransaction, UUID> {
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByOrderNumber(String orderNumber);

    Optional<PaymentTransaction> findByAtrn(String atrn);
}
}
