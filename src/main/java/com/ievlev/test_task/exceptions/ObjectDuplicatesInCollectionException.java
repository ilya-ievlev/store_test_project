package com.ievlev.test_task.exceptions;

public class ObjectDuplicatesInCollectionException extends RuntimeException {
    public ObjectDuplicatesInCollectionException() {
    }

    public ObjectDuplicatesInCollectionException(String message) {
        super(message);
    }

    public ObjectDuplicatesInCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectDuplicatesInCollectionException(Throwable cause) {
        super(cause);
    }


}
