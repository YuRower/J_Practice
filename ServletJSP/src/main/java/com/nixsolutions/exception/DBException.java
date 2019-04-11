package com.nixsolutions.exception;

public class DBException extends RuntimeException {

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
