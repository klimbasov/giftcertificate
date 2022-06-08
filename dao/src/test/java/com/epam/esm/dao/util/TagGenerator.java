package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.Tag;

public class TagGenerator {
    public static Tag generate() {
        return Tag.builder()
                .name("certificate")
                .build();
    }
}
