package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;

import java.sql.Date;
import java.time.LocalDateTime;

public class TagGenerator {
    public static Tag generate(){
        return Tag.builder()
                .name("certificate")
                .build();
    }
}
