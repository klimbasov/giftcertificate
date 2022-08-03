package com.epam.esm.dao;

import com.epam.esm.dao.entity.Role;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Specifies dao pattern of role entity.
 */
public interface RoleDao {
    /**
     * Issue a read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of entity, associated with id.
     */
    Optional<Role> read(long id);


    /**
     * Issue a read-all operation
     *
     * @param offset - how many entities mast be skipped
     * @param limit  - how many entities mast be got
     * @param name   - subname of entity
     * @return list of entities, conforming to the options.
     */
    List<Role> read(int offset, int limit, String name);


    /**
     * Issue a count operation. Included to maintain pagination mechanic
     *
     * @param name - subname of entity
     * @return quantity of elements
     */
    long count(String name);
}
