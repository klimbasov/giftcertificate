package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class ObjectAlreadyExistException extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public ObjectAlreadyExistException() {
        super(STATUS);
    }

    public ObjectAlreadyExistException(String message) {
        super(message, STATUS);
    }

    public ObjectAlreadyExistException(Exception exception) {
        super(exception, STATUS);
    }

    public ObjectAlreadyExistException(String message, Exception exception) {
        super(message, exception, STATUS);
    }
}
