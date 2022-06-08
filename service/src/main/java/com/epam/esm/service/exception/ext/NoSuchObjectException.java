package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class NoSuchObjectException extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NoSuchObjectException() {
        super(STATUS);
    }

    public NoSuchObjectException(String message) {
        super(message, STATUS);
    }

    public NoSuchObjectException(Exception exception) {
        super(exception, STATUS);
    }

    public NoSuchObjectException(String message, Exception exception) {
        super(message, exception, STATUS);
    }
}
