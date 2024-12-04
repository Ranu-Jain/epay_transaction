
package com.epay.transaction.repository;

import com.epay.transaction.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Class Name:CustomerRepository
 * Description:
 * Author:V1014352(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * Version:1.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmailOrPhoneNumberAndMerchantId(String email, String phone, String mId);

    Optional<Customer> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

}
