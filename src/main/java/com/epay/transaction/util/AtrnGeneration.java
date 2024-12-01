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
    public class ATRNGenerator {

    public static String generateATRN() {
        Random random = new Random();

        // Ensure the first digit is between 1 and 9
        int firstDigit = random.nextInt(9) + 1; 

        // Generate the remaining 14 digits
        StringBuilder atrn = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 0; i < 14; i++) {
            atrn.append(random.nextInt(10)); // Digits 0 to 9
        }

        return atrn.toString();
    }
}

}
