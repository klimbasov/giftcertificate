package com.epam.esm.dao.exception;

public class DaoException extends Exception {
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Exception exception) {
        super(exception);
    }
}
