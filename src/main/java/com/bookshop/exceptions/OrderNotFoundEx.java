package com.bookshop.exceptions;

public class OrderNotFoundEx extends RuntimeException {
    public OrderNotFoundEx(String message) {
        super(message);
    }

    public OrderNotFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundEx(Throwable cause) {
        super(cause);
    }
}
