package com.epam.esm.service.exception;

public class ServiceException extends Exception{

    public ServiceException(){}
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(Exception exception){
        super(exception);
    }
    public ServiceException(String message, Exception exception){
        super(message, exception);
    }
}
