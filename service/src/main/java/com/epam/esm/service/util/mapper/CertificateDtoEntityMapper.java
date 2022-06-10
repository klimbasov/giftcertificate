package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.CertificateDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.LocalDateTimeFormatter.toIso8601Str;
import static java.util.Objects.nonNull;

public class CertificateDtoEntityMapper {

    private CertificateDtoEntityMapper() {
    }

    public static Certificate mapToEntity(CertificateDto certificateDto) {
        Certificate.CertificateBuilder<?, ?> builder = getNewCertificateBuilder();
        return buildCertificate(certificateDto, builder);
    }

    public static Certificate mapToEntity(CertificateDto certificateDto, Certificate certificate) {
        Certificate.CertificateBuilder<?, ?> builder = getExistingCertificateBuilder(certificate);
        return buildCertificate(certificateDto, builder);
    }

    private static Certificate.CertificateBuilder<?, ?> getExistingCertificateBuilder(Certificate certificate) {
        Certificate.CertificateBuilder<?, ?> builder = certificate.toBuilder();
        builder.createDate(certificate.getCreateDate());
        builder.lastUpdateDate(LocalDateTime.now());
        return builder;
    }

    public static CertificateDto mapToDto(Certificate certificate, Set<Tag> tags) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .createDate(toIso8601Str(certificate.getCreateDate()))
                .lastUpdateDate(toIso8601Str(certificate.getLastUpdateDate()))
                .duration(certificate.getDuration())
                .tags(tags.stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

    public static CertificateDto mapToDto(Certificate certificate, List<String> tags) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .createDate(toIso8601Str(certificate.getCreateDate()))
                .lastUpdateDate(toIso8601Str(certificate.getLastUpdateDate()))
                .duration(certificate.getDuration())
                .tags(tags)
                .build();
    }

    private static Certificate.CertificateBuilder<?, ?> getNewCertificateBuilder() {
        Certificate.CertificateBuilder<?, ?> builder = Certificate.builder();
        builder.createDate(LocalDateTime.now());
        builder.lastUpdateDate(LocalDateTime.now());
        return builder;
    }

    private static Certificate buildCertificate(CertificateDto certificateDto, Certificate.CertificateBuilder<?, ?> builder) {
        if (nonNull(certificateDto.getName())) {
            builder.name(certificateDto.getName());
        }
        if (nonNull(certificateDto.getDescription())) {
            builder.description(certificateDto.getDescription());
        }
        if (nonNull(certificateDto.getPrice())) {
            builder.price(certificateDto.getPrice());
        }
        if (nonNull(certificateDto.getDuration())) {
            builder.duration(certificateDto.getDuration());
        }
        return builder.build();
    }
}
