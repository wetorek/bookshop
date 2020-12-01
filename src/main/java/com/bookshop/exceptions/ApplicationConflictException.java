package com.bookshop.exceptions;

public class ApplicationConflictException extends RuntimeException {
    public ApplicationConflictException(String message) {
        super(message);
    }

    public ApplicationConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationConflictException(Throwable cause) {
        super(cause);
    }
}
