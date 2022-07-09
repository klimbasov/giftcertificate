package com.epam.esm.dao;

import com.epam.esm.dao.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Specifies dao pattern of tag entity.
 */
public interface OrderDao {
    /**
     * Issue an order create operation.
     *
     * @param order - order entity to be added.
     * @return - tag entity. Order id is provided.
     */
    Optional<Order> create(Order order);

    /**
     * Issue an order read-by-id operation.
     *
     * @param id - id, associated with an object.
     * @return optional of tag entity, associated with id.
     */
    Optional<Order> read(long id);

    /**
     * Issue an order read operation.
     *
     * @param limit  - maximum size of spotting list.
     * @param offset - offset in entities in conforming list.
     * @return list of tags entities, conforming to the options.
     */
    List<Order> read(int offset, int limit);

    /**
     * Issue an order read-by-user-id operation.
     *
     * @param limit  - maximum size of spotting list.
     * @param offset - offset in entities in conforming list.
     * @param userId - id of a user, orders mast refers to.
     * @return list of tags entities, conforming to the options.
     */
    List<Order> read(int offset, int limit, long userId);

    /**
     * Issue an order delete operation.
     *
     * @param id - id, associated with an object.
     * @return 0 if object mapped to provided id does not exist. Otherwise, return 1.
     */
    int delete(long id);


    /**
     * Issue an order count operation. Included to maintain pagination mechanic
     *
     * @return quantity of elements, that corresponds to name
     */
    long count();

    /**
     * Issue an order count-by-user-id operation. Included to maintain pagination mechanic
     *
     * @param userId - id of a user, orders mast refers to.
     * @return quantity of elements, that corresponds to name
     */
    long count(long userId);
}
