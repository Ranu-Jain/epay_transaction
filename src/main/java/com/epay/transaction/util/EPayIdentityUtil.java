package com.epay.transaction.util;

import com.sbi.epay.authentication.model.EPayPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class EPayIdentityUtil {

    public static EPayPrincipal getUserPrincipal() {
       return (EPayPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
