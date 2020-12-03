package com.bookshop.exceptions;

public class ApplicationBadRequestException extends RuntimeException{
    public ApplicationBadRequestException(String message) {
        super(message);
    }

    public ApplicationBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationBadRequestException(Throwable cause) {
        super(cause);
    }
}
