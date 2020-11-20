package com.bookshop.exceptions;

public class BookConflictException extends RuntimeException {
    public BookConflictException(String message) {
        super(message);
    }

    public BookConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookConflictException(Throwable cause) {
        super(cause);
    }
}
