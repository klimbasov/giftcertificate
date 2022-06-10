package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.NoSuchObjectException;

import java.util.List;

/**
 * Specifies a certificate CRUD business logic.
 */
public interface CertificateService {
    /**
     * Issue a certificate create operation.
     *
     * @param certificateDto - dto containing certificate to be added.
     * @return fully initialized dto. Creation and last update dates and certificate id are provided.
     * @throws com.epam.esm.service.exception.ext.IllegalArgumentException - if input dto state is invalid.
     * For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    CertificateDto add(CertificateDto certificateDto);

    /**
     * Issue a certificate update operation.
     *
     * @param certificateDto - partially initialized dto, containing fields to be updated. Id field must be initialized.
     * @throws NoSuchObjectException          - if there is no object, associated with the provided id.
     * @throws com.epam.esm.service.exception.ext.IllegalArgumentException - if input dto state is invalid.
     * For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    void put(CertificateDto certificateDto);

    /**
     * Issue a certificate read-by-id operation.
     *
     * @param id - id value, associated with an object.
     * @return - fully initialized dto, associated with input id.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    CertificateDto get(Integer id);

    /**
     * Issue a certificate read-by-option operation.
     *
     * @param searchOptions - object, containing options, the search certificates must conform to.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    List<CertificateDto> get(SearchOptions searchOptions);

    /**
     * Issue a certificate delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    void delete(Integer id);
}
