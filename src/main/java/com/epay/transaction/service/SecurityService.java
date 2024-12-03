package com.epay.transaction.service;

import com.sbi.epay.authentication.model.EPayPrincipal;
import com.sbi.epay.authentication.service.AuthenticationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class SecurityService implements AuthenticationUserService {

    @Override
    public Optional<EPayPrincipal> loadUserByUserName(String authKey) {
        EPayPrincipal authenticateEntity = new EPayPrincipal();
        authenticateEntity.setAuthenticationKey(authKey);
        return Optional.of(authenticateEntity);
    }
}