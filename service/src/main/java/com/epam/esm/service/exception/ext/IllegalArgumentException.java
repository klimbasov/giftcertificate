package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public IllegalArgumentException() {
        super(STATUS);
    }

    public IllegalArgumentException(String message) {
        super(message, STATUS);
    }

    public IllegalArgumentException(Exception exception) {
        super(exception, STATUS);
    }

    public IllegalArgumentException(String message, Exception exception) {
        super(message, exception, STATUS);
    }

}
