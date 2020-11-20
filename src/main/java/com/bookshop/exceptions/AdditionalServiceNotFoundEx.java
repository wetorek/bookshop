package com.bookshop.exceptions;

public class AdditionalServiceNotFoundEx extends RuntimeException {

    public AdditionalServiceNotFoundEx(String message) {
        super(message);
    }

    public AdditionalServiceNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public AdditionalServiceNotFoundEx(Throwable cause) {
        super(cause);
    }
}