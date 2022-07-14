package com.epam.esm.service;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import org.springframework.hateoas.PagedModel;

/**
 * Specifies a certificate CRUD business logic.
 */
public interface CertificateService {
    /**
     * Issue a certificate create operation.
     *
     * @param certificateDto - dto containing certificate to be added.
     * @return fully initialized dto. Creation and last update dates and certificate id are provided.
     * @throws InvalidRequestException - if input dto state is invalid.
     *                                 For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    CertificateDto create(CertificateDto certificateDto);

    /**
     * Issue a certificate update operation.
     *
     * @param certificateDto - partially initialized dto, containing fields to be updated. Id field must be initialized.
     * @return dto, that represents updated entity.
     * @throws NoSuchObjectException   - if there is no object, associated with the provided id.
     * @throws InvalidRequestException - if input dto state is invalid.
     *                                 For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    CertificateDto update(CertificateDto certificateDto);

    /**
     * Issue a certificate read-by-id operation.
     *
     * @param id - id value, associated with an object.
     * @return - fully initialized dto, associated with input id.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    CertificateDto read(Long id);

    /**
     * Issue a certificate read-by-option operation.
     *
     * @param searchOptions - object, containing options, the search certificates must conform to.
     * @param tags          - partial name of tag, search certificates have to respect.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    PagedModel<CertificateDto> read(SearchOptions searchOptions, String[] tags);

    /**
     * Issue a certificate delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    void delete(Long id);
}
