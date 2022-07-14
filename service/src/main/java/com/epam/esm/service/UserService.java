package com.epam.esm.service;

import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import org.springframework.hateoas.PagedModel;

/**
 * Specifies a user read business logic.
 */
public interface UserService {
    /**
     * Issue a user read-by-option operation.
     *
     * @param options - object, containing options, the search users must conform to.
     * @return - list of fully initialized dtos, conformed to provided options.
     */
    PagedModel<UserDto> read(SearchOptions options);

    /**
     * Issue a user delete operation.
     *
     * @param id - id value, associated with an object.
     * @throws NoSuchObjectException - if there is no object, associated with the provided id.
     */
    UserDto read(long id);
}
