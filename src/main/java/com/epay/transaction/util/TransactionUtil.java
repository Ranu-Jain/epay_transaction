package com.epay.transaction.util;

import com.sbi.epay.authentication.model.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtil {

    public String getPrincipleUserName(){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getUsername();
    }

}
