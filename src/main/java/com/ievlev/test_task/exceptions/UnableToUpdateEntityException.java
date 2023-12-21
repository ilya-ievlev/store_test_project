package com.ievlev.test_task.exceptions;

public class UnableToUpdateEntityException extends RuntimeException {
    public UnableToUpdateEntityException() {
    }

    public UnableToUpdateEntityException(String message) {
        super(message);
    }

    public UnableToUpdateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToUpdateEntityException(Throwable cause) {
        super(cause);
    }
}
