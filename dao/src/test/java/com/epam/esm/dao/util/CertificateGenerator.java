package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.Certificate;

import java.sql.Date;
import java.time.LocalDateTime;

public class CertificateGenerator {
    public static Certificate generate(){
        return Certificate.builder()
                .id(1)
                .name("certificate")
                .duration(2)
                .price(0.01f)
                .lastUpdateDate(Date.valueOf(LocalDateTime.now().toLocalDate()))
                .createDate(Date.valueOf(LocalDateTime.now().toLocalDate()))
                .description("some cool certificate for u.")
                .build();
    }
}
