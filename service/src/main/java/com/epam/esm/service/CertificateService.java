package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.NoSuchObjectException;

import java.util.List;
import java.util.Optional;

public interface CertificateService {
    int add(CertificateDto certificateDto);

    void put(CertificateDto certificateDto) throws NoSuchObjectException;

    Optional<CertificateDto> get(Integer id);

    void delete(Integer id);

    Optional<List<CertificateDto>> get(SearchOptions searchOptions);
}
