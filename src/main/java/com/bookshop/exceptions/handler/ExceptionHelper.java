package com.bookshop.exceptions.handler;

import com.bookshop.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {BookNotFoundEx.class})
    private ResponseEntity<Object> handleBookNotFound(BookNotFoundEx ex) {
        log.error("Item not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BookConflictEx.class})
    private ResponseEntity<Object> handleBookConflict(BookConflictEx ex) {
        log.error("Conflict in item: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {AdditionalServiceNotFoundEx.class})
    private ResponseEntity<Object> handleServiceNotFound(AdditionalServiceNotFoundEx ex) {
        log.error("Item not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AdditionalServiceConflictEx.class})
    private ResponseEntity<Object> handleServiceConflict(AdditionalServiceConflictEx ex) {
        log.error("Conflict in item: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = InvalidCartEx.class)
    private ResponseEntity<Object> handleCartConflict(InvalidCartEx ex) {
        log.error("Conflict in cart: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = OrderChangeNotAllowedEx.class)
    private ResponseEntity<Object> handleOrderStatusChangeConflict(OrderChangeNotAllowedEx ex) {
        log.error("Conflict in order: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = OrderNotFoundEx.class)
    private ResponseEntity<Object> orderNotFoundHandler(OrderNotFoundEx ex) {
        log.error("Order not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ApplicationNotFoundException.class)
    private ResponseEntity<Object> notFoundHandler(ApplicationNotFoundException ex) {
        log.error("Not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ApplicationConflictException.class)
    private ResponseEntity<Object> conflictExceptionHandler(ApplicationConflictException ex) {
        log.error("Conflict in: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
