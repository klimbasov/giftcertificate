package com.epam.esm.controller;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    private static final String BASE_NAME = "error.";
    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public Map<String, String> handleServiceRuntimeException(ServiceRuntimeException e, Locale locale) {
        return handleExceptionInternal(BASE_NAME + e.getStatus().name().toLowerCase(),
                e.getStatus().value(),
                locale);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String, String> handleNoHandlerFoundException(Exception e, Locale locale) {
        return handleExceptionInternal("error.not_found", HttpStatus.NOT_FOUND.value(), locale);
    }

    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception e, Locale locale) {
        return handleExceptionInternal("error.unexpected", HttpStatus.INTERNAL_SERVER_ERROR.value(), locale);
    }

    private Map<String, String> handleExceptionInternal(String messageKey, int status, Locale locale) {
        Map<String, String> errorResponse = new HashMap<>();
        String message = messageSource.getMessage(messageKey, null, locale);
        errorResponse.put("errorMessage", message);
        errorResponse.put("errorCode", Integer.toString(status));
        return errorResponse;
    }
}
