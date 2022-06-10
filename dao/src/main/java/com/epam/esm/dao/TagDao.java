package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Specifies dao pattern of tag entity.
 */
public interface TagDao {

    /**
     * Issue a tag create operation.
     *
     * @param tag - tag entity to be added.
     * @return - tag entity. Tag id is provided.
     */
    Optional<Tag> create(Tag tag);

    /**
     * Issue a certificate read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of tag entity, associated with id.
     */
    Optional<Tag> read(int id);

    /**
     * Issue a certificate read-bu-name operation.
     *
     * @param name - partial name of the search tags.
     * @return list of tags entities, conforming to the options.
     */
    List<Tag> read(String name);

    /**
     * Issue a certificate read-by-certificateId operation.
     *
     * @param certificateId - id, associated with the certificate, which tags required to be received.
     * @return list of tags entities, conforming to the options.
     */
    Set<Tag> readByCertificateId(Integer certificateId);

    /**
     * Issue a certificate delete operation.
     *
     * @param id - id, associated with an object.
     * @return 0 if object mapped to provided id does not exist. Otherwise, return 1.
     */
    int delete(int id);
}
