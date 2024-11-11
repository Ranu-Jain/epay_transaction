package com.epay.transaction.repositary;

import com.epay.transaction.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Class Name:CustomerRepository
 * Description:
 * Author:V1014352(Ranjan Kumar)
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * Version:1.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailAndPhoneNumber(String email, String phoneNumber);

    boolean existsByCustomerId(String customerId);

}