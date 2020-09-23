package com.bookshop.controller;

import com.bookshop.entity.Book;
import com.bookshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
@AllArgsConstructor
public class BookController {

    BookService bookService;

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("/add")
    public ResponseEntity signup (@RequestBody Book book){
        bookService.save(book);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
