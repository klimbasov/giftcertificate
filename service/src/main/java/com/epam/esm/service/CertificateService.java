package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.exception.ext.ObjectCanNotBeCreatedException;

import java.util.List;

public interface CertificateService {
    CertificateDto add(CertificateDto certificateDto) throws ObjectCanNotBeCreatedException;

    void put(CertificateDto certificateDto) throws NoSuchObjectException, ObjectCanNotBeCreatedException;

    CertificateDto get(Integer id) throws NoSuchObjectException;

    void delete(Integer id);

    List<CertificateDto> get(SearchOptions searchOptions) throws NoSuchObjectException;
}
