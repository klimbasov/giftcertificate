package com.epam.esm.domain.mapper.exception;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class MappingException extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public MappingException() {
        super(STATUS);
    }

    public MappingException(String message) {
        super(message, STATUS);
    }

    public MappingException(Exception exception) {
        super(exception, STATUS);
    }

    public MappingException(String message, Exception exception) {
        super(message, exception, STATUS);
    }
}
