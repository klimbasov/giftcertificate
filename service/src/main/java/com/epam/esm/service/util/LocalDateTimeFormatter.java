package com.epam.esm.service.util;

import java.time.LocalDateTime;

public class LocalDateTimeFormatter {

    private LocalDateTimeFormatter() {
    }

    public static String toIso8601Str(final LocalDateTime localDateTime) {
        return localDateTime.toString();
    }
}
