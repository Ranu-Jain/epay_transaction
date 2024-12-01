package com.epay.transaction.util;

import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.AuthenticationUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class Name:SecurityService
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
@Service
public class SecurityService implements AuthenticationUserService {
    @Override
    public Optional<EPayPrincipal> loadUserByUserName(String s) {
        EPayPrincipal authenticateEntity = new EPayPrincipal();
        authenticateEntity.setAuthenticationKey("232323");
        authenticateEntity.setUserRole(List.of("Admin"));
        return Optional.of(authenticateEntity);
    }
}
