package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public InvalidRequestException() {
        super(STATUS);
    }

    public InvalidRequestException(String message) {
        super(message, STATUS);
    }

    public InvalidRequestException(Exception exception) {
        super(exception, STATUS);
    }

    public InvalidRequestException(String message, Exception exception) {
        super(message, exception, STATUS);
    }

}
