package com.epam.esm.service.exception;

import org.springframework.http.HttpStatus;

public abstract class ServiceRuntimeException extends RuntimeException {
    private final HttpStatus status;

    protected ServiceRuntimeException(HttpStatus status) {
        this.status = status;
    }

    protected ServiceRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    protected ServiceRuntimeException(Exception exception, HttpStatus status) {
        super(exception);
        this.status = status;
    }

    protected ServiceRuntimeException(String message, Exception exception, HttpStatus status) {
        super(message, exception);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
