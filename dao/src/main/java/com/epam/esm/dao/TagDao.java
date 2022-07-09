package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Specifies dao pattern of tag entity.
 */
public interface TagDao {

    /**
     * Issue a tag create operation.
     *
     * @param tag - tag entity to be added.
     * @return - optional tag entity. Tag id is provided.
     */
    Optional<Tag> create(Tag tag);

    /**
     * Issue a tag read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of tag entity, associated with id.
     */
    Optional<Tag> read(long id);

    /**
     * Issue a tag read-bu-name operation.
     *
     * @param limit    - maximum size of spotting list.
     * @param offset   - offset in entities in conforming list.
     * @param name     - partial name of the search entities.
     * @param ordering - if true, ordering is direct. Otherwise, ordering is inverted.
     * @return list of tags entities, conforming to the options.
     */
    List<Tag> read(String name, int offset, int limit, boolean ordering);

    /**
     * Issue a tag delete operation.
     *
     * @param id - id, associated with an object.
     * @return 0 if object mapped to provided id does not exist. Otherwise, return 1.
     */
    int delete(long id);


    /**
     * Issue a tag count operation. Included to maintain pagination mechanic
     *
     * @param name partial name of the search tags.
     * @return quantity of elements, that corresponds to name
     */
    long count(String name);

    /**
     * Issue a tag read-most-used-of-user-with-Highest-cost operation.
     *
     * @return optional tag entity.
     */
    Optional<Tag> readMostUsedTagOfUserWithHighestOrderCost();
}
