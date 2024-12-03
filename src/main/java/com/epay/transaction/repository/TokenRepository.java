/*
 *
 *  Copyright (c) [2024] [State Bank of India]
 *  All rights reserved.
 *
 *  Author:@V0000001(Shilpa Kothre)
 *  Version:1.0
 *
 */


package com.epay.transaction.repository;

import com.epay.transaction.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("SELECT t FROM Token t WHERE t.merchantId =:mid AND t.status = 'ACTIVE'")
    Token findActiveTokenByMerchantId(@Param("mid") String mid);

    @Query("SELECT t FROM Token t WHERE t.generatedToken =:generatedToken AND t.status = 'ACTIVE'")
    Token findActiveTokenByGeneratedToken(@Param("generatedToken") String generatedToken);

    List<Token> findByStatusAndTokenExpiryTimeLessThan(String active, Long currentTimeStamp);

}
