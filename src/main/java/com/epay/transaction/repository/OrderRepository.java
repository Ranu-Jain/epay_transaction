package com.epay.transaction.repository;

import com.epay.transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Class Name: OrderRepository
 * *
 * Description: OrderRepository class for manage order data persistence and retrieval in a database.
 * *
 * Author: V1012904(Shital suryawanshi)
 * Copyright (c) 2024 [State Bank of India]
 * All rights reserved
 * *
 * Version:1.0
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

   // TODO: To find active order, temporary using status for now, we will use order expiry later.(Change require in Authentication Utility)
    @Query("SELECT t FROM Order t WHERE t.orderHash =:orderHash AND t.status IN ('CREATED', 'ATTEMPTED')")
    Optional<Order> findActiveOrderByHash(@Param("orderHash") String orderHash);

    @Query("SELECT t FROM Order t WHERE t.sbiOrderRefNumber =:sbiOrderRefNumber AND t.status IN ('CREATED', 'ATTEMPTED')")
    Optional<Order> findActiveOrderBySBIOrderRefNumber(@Param("sbiOrderRefNumber") String sbiOrderRefNumber);

    Optional<Order> findByOrderRefNumber(String id);

    boolean existsBySbiOrderRefNumber(String sbiOrderRefNum);

}

