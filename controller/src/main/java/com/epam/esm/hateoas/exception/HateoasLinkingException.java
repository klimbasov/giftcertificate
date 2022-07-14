package com.epam.esm.hateoas.exception;

public class HateoasLinkingException extends RuntimeException{
    public HateoasLinkingException() {
        super();
    }

    public HateoasLinkingException(String message) {
        super(message);
    }

    public HateoasLinkingException(Exception exception) {
        super(exception);
    }

    public HateoasLinkingException(String message, Exception exception) {
        super(message, exception);
    }
}
