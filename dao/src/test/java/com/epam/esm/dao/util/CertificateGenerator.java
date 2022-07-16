package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.Certificate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CertificateGenerator {
    public static Certificate generate() {
        return Certificate.builder()
                .id(1)
                .name("certificate")
                .duration(2)
                .price(0.01f)
                .lastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .createDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .description("some cool certificate for u.")
                .build();
    }
}
