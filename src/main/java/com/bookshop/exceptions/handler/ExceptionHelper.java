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

    @ExceptionHandler(value = {BookNotFoundException.class})
    private ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        log.error("Item not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BookConflictException.class})
    private ResponseEntity<Object> handleBookConflict(BookConflictException ex) {
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

    @ExceptionHandler(value = InvalidCartException.class)
    private ResponseEntity<Object> handleCartConflict (InvalidCartException ex){
        log.error("Conflict in cart: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
