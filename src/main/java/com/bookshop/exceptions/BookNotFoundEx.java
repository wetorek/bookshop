package com.bookshop.exceptions;

public class BookNotFoundEx extends RuntimeException {

    public BookNotFoundEx(String message) {
        super(message);
    }

    public BookNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundEx(Throwable cause) {
        super(cause);
    }
}
