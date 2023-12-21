package com.ievlev.test_task.exceptions;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException() {
    }

    public UserIdNotFoundException(String message) {
        super(message);
    }

    public UserIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdNotFoundException(Throwable cause) {
        super(cause);
    }
}
