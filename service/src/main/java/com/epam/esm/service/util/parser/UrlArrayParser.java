package com.epam.esm.service.util.parser;

import static java.util.Objects.isNull;

public class UrlArrayParser {
    private UrlArrayParser() {

    }

    public static String[] parse(String strRep) {
        return isNull(strRep) ? new String[0] : strRep.split(":");
    }
}
