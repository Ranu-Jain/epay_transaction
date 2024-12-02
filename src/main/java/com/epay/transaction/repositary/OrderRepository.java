package com.epay.transaction.repositary;

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

    @Query("SELECT t FROM Order t WHERE t.hash = :orderHash AND t.status IN ('ACTIVE', 'BOOKED')")
    Optional<Order> findActiveOrderByHash(@Param("orderHash") String orderHash);

    Optional<Order> findByOrderRefNumber(String id);

    boolean existsBySbiOrderRefNum(String sbiOrderRefNum);

}

