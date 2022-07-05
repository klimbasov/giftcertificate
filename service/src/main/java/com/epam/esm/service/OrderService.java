package com.epam.esm.service;

import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.exception.ext.InvalidRequestException;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import org.springframework.hateoas.PagedModel;

/**
 * Specifies an order CRD business logic.
 */
public interface OrderService {
    /**
     * Issue an order create operation.
     *
     * @param orderInputDto - dto containing order to be added.
     * @return fully initialized dto. Order id is provided.
     * @throws InvalidRequestException - if input dto state is invalid.
     *                                 For validation requirements see {@link com.epam.esm.service.util.validator.ArgumentValidator}
     */
    OrderDto create(OrderDto orderInputDto);

    /**
     * Issue an order read-by-id operation.
     *
     * @param id - id value, associated with an object.
     * @return - fully initialized dto, associated with input id.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    OrderDto read(Long id);

    /**
     * Issue an order read-by-option operation.
     *
     * @param options - object, containing options, the search orders must conform to.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    PagedModel<OrderDto> read(SearchOptions options);

    /**
     * Issue an order read-by-user-id-and-option operation.
     *
     * @param options - object, containing options, the search orders must conform to.
     * @param userId  - id of the orders owning user.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    PagedModel<OrderDto> read(SearchOptions options, long userId);

    /**
     * Issue an order delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    void delete(Long id);
}
