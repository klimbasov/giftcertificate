package com.epam.esm.controller;

import com.epam.esm.service.exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    public static final String BASE_NAME = "error.";
    public static final String VERSION = "01";
    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public Map<String, String> handleServiceRuntimeException(ServiceRuntimeException e, Locale locale) {
        Map<String, String> errorResponse = new HashMap<>();
        String message = messageSource.getMessage(BASE_NAME + e.getStatus().name().toLowerCase(), null, locale);
        errorResponse.put("errorMessage", message);
        errorResponse.put("errorCode", e.getStatus() + VERSION);
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception ex, Locale locale) {
        Map<String, String> errorResponse = new HashMap<>();
        String message = messageSource.getMessage("error.unexpected", null, locale);

        errorResponse.put("errorMessage", message);
        errorResponse.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value() + VERSION);
        return errorResponse;
    }
}
