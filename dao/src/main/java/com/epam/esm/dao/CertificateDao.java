package com.epam.esm.dao;

import com.epam.esm.dao.entity.Certificate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Specifies dao pattern of certificate entity.
 */
public interface CertificateDao {

    /**
     * Issue a certificate create operation.
     *
     * @param certificate - object, representing certificate entity.
     * @param tagIds      - tags ids, that have to be associated with the certificate.
     * @return optional of certificate entity. Certificate id is provided. Optional is empty, if certificate entity
     * was not added.
     */
    Optional<Certificate> create(Certificate certificate, Set<Long> tagIds);

    /**
     * Issue a certificate read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of certificate entity, associated with id.
     */
    Optional<Certificate> read(long id);

    /**
     * Issue a certificate read-by-option operation.
     *
     * @param name - partial name of the search certificates.
     * @param desc - partial description of the search certificates.
     * @param tag  - partial name of tag, search certificates have to respect.
     * @return list of certificates entities, respective to the options.
     */
    List<Certificate> read(String name, String desc, String tag, int offset, int limit);

    /**
     * Issue a certificate delete operation.
     *
     * @param id - id, associated with an object.
     * @return 0 if object mapped to provided id does not exist. Otherwise, return 1.
     */
    int delete(long id);

    /**
     * Issue a certificate update operation.
     *
     * @param certificate - object, representing modified certificate entity.
     * @param tagIds      - modified tag ids set.
     */
    void update(Certificate certificate, Set<Long> tagIds);
}
