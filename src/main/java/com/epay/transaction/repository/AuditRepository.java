package com.epay.transaction.repository;

import com.epay.transaction.entity.TokenAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Class Name:AuditRepository
 * *
 * Description:
 * *
 * Author:(Shilpa)
 * <p>
 * Copyright (c) 2024 [State Bank of INdia]
 * All right reserved
 * *
 * Version:1.0
 */
@Repository
public interface AuditRepository extends JpaRepository<TokenAudit, UUID> {
}
