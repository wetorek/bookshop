package com.bookshop.exceptions;

public class AdditionalServiceConflictEx extends RuntimeException {
    public AdditionalServiceConflictEx(String message) {
        super(message);
    }

    public AdditionalServiceConflictEx(String message, Throwable cause) {
        super(message, cause);
    }

    public AdditionalServiceConflictEx(Throwable cause) {
        super(cause);
    }
}
