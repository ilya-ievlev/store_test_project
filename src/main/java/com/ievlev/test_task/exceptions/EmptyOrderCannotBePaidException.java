package com.ievlev.test_task.exceptions;

public class EmptyOrderCannotBePaidException extends RuntimeException {
    public EmptyOrderCannotBePaidException() {
    }

    public EmptyOrderCannotBePaidException(String message) {
        super(message);
    }

    public EmptyOrderCannotBePaidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyOrderCannotBePaidException(Throwable cause) {
        super(cause);
    }

}
