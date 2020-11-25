package com.bookshop.exceptions;

public class OrderChangeNotAllowedEx extends RuntimeException {
    public OrderChangeNotAllowedEx(String message) {
        super(message);
    }

    public OrderChangeNotAllowedEx(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderChangeNotAllowedEx(Throwable cause) {
        super(cause);
    }
}
