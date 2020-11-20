package com.bookshop.exceptions.handler;

import com.bookshop.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ExceptionHelper {

    @ExceptionHandler(value = {BookNotFoundException.class})
    private ResponseEntity<Object> handleConflict (BookNotFoundException ex){
        log.error("Book not found: " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
