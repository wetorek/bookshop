package com.bookshop.exceptions;

public class InvalidCartEx extends RuntimeException {
    public InvalidCartEx(String message) {
        super(message);
    }

    public InvalidCartEx(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCartEx(Throwable cause) {
        super(cause);
    }
}
