package com.epam.esm.dao.util.formatter;

import org.springframework.lang.NonNull;

public class AlikeStringSqlFormatter {
    private AlikeStringSqlFormatter(){}

    public static String wrap(@NonNull String str) {
        return "%" + str + "%";
    }
}
