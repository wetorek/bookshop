package com.bookshop.exceptions.handler;

import com.bookshop.exceptions.BookConflictException;
import com.bookshop.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(value = {BookNotFoundException.class})
    private ResponseEntity<Object> handleNotFoundConflict(BookNotFoundException ex) {
        log.error("Book not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BookConflictException.class})
    private ResponseEntity<Object> handleBookConflict(BookConflictException ex) {
        log.error("Conflict in book: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
