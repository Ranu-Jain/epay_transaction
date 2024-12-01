package com.epay.transaction.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * Class Name:AtrnGeneration
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
@Component
@RequiredArgsConstructor
public class AtrnGeneration {
    public static String generateAtrnNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder atrnNumber = new StringBuilder();

        while (atrnNumber.length() < 14) {
            int ch = (char) (random.nextInt(36) + '0');
            if (ch > '9') ch += ('a' - '9' - 1);
            atrnNumber.append(ch);
        }

        if (atrnNumber.charAt(0) == '0') {
            atrnNumber.setCharAt(0, (char) (random.nextInt(9) + '1'));
        }

        return atrnNumber.toString();
    }

}
