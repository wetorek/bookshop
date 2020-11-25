package com.bookshop.exceptions;

public class BookConflictEx extends RuntimeException {
    public BookConflictEx(String message) {
        super(message);
    }

    public BookConflictEx(String message, Throwable cause) {
        super(message, cause);
    }

    public BookConflictEx(Throwable cause) {
        super(cause);
    }
}
