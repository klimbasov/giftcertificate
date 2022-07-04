package com.epam.esm.service.util.mapper;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.CertificateDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.LocalDateTimeFormatter.toIso8601Str;
import static java.util.Objects.nonNull;

public class CertificateDtoEntityMapper {

    private CertificateDtoEntityMapper() {
    }

    public static Certificate mapToEntity(CertificateDto certificateDto) {
        Certificate.CertificateBuilder builder = getNewCertificateBuilder();
        return buildCertificate(certificateDto, builder);
    }

    public static void mapToEntity(CertificateDto certificateDto, Certificate certificate) {
        if (nonNull(certificateDto.getName())) {
            certificate.setName(certificateDto.getName());
        }
        if (nonNull(certificateDto.getDescription())) {
            certificate.setDescription(certificateDto.getDescription());
        }
        if (certificateDto.getDuration() != 0) {
            certificate.setDuration(certificateDto.getDuration());
        }
        if (certificateDto.getPrice() != 0) {
            certificate.setPrice(certificateDto.getPrice());
        }
    }

    public static CertificateDto mapToDto(Certificate certificate) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .createDate(toIso8601Str(certificate.getCreateDate()))
                .lastUpdateDate(toIso8601Str(certificate.getLastUpdateDate()))
                .duration(certificate.getDuration())
                .tags(certificate.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
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

    private static Certificate.CertificateBuilder getNewCertificateBuilder() {
        Certificate.CertificateBuilder builder = Certificate.builder();
        builder.createDate(LocalDateTime.now());
        builder.lastUpdateDate(LocalDateTime.now());
        return builder;
    }

    private static Certificate buildCertificate(CertificateDto certificateDto, Certificate.CertificateBuilder builder) {
        if (nonNull(certificateDto.getName())) {
            builder.name(certificateDto.getName());
        }
        if (nonNull(certificateDto.getDescription())) {
            builder.description(certificateDto.getDescription());
        }
        if (certificateDto.getDuration() != 0) {
            builder.duration(certificateDto.getDuration());
        }
        if (certificateDto.getPrice() != 0) {
            builder.price(certificateDto.getPrice());
        }
        return builder.build();
    }
}
