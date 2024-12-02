package com.epay.transaction.util;

import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeUtils {

    public Long getCurrentTimeInMills() {
        return new Date().getTime();
    }
}
