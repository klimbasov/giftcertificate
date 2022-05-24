package com.epam.esm.service.exception;

public class NoSuchObjectException extends Exception{
    public NoSuchObjectException(){}
    public NoSuchObjectException(String message){
        super(message);
    }
    public NoSuchObjectException(Exception exception){
        super(exception);
    }
}
