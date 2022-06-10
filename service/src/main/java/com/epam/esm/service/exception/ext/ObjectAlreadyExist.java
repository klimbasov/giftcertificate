package com.epam.esm.service.exception.ext;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.http.HttpStatus;

public class ObjectAlreadyExist extends ServiceRuntimeException {
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public ObjectAlreadyExist() {
        super(STATUS);
    }

    public ObjectAlreadyExist(String message) {
        super(message, STATUS);
    }

    public ObjectAlreadyExist(Exception exception) {
        super(exception, STATUS);
    }

    public ObjectAlreadyExist(String message, Exception exception) {
        super(message, exception, STATUS);
    }
}
