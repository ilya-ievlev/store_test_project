package com.ievlev.test_task.exceptions;

public class AddToOrderException extends RuntimeException {
    public AddToOrderException() {
    }

    public AddToOrderException(String message) {
        super(message);
    }

    public AddToOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddToOrderException(Throwable cause) {
        super(cause);
    }

}
