package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class ObjectCanNotBeCreatedException extends ServiceRuntimeException {
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public ObjectCanNotBeCreatedException() {
        super(STATUS);
    }

    public ObjectCanNotBeCreatedException(String message) {
        super(message, STATUS);
    }

    public ObjectCanNotBeCreatedException(Exception exception) {
        super(exception, STATUS);
    }

    public ObjectCanNotBeCreatedException(String message, Exception exception) {
        super(message, exception, STATUS);
    }
}
