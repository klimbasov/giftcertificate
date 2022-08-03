package com.epam.esm.dao;

import com.epam.esm.dao.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Specifies dao pattern of user entity.
 */
public interface UserDao {
    /**
     * Issue a user read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of user entity, associated with id.
     */
    Optional<User> read(long id);


    /**
     * Issue a user read-all operation
     *
     * @param offset - how many users mast be skipped
     * @param limit  - how many users mast be got
     * @param name   - subname of user
     * @return list of user entities, conforming to the options.
     */
    List<User> read(int offset, int limit, String name);


    /**
     * Issue user count operation. Included to maintain pagination mechanic
     *
     * @param name - subname of user
     * @return quantity of elements
     */
    long count(String name);

    /**
     * Issue read-by-strict-name operation.
     *
     * @param name - name of user
     * @return optional of user entity, associated with name.
     */
    Optional<User> readByStrictName(String name);
}
