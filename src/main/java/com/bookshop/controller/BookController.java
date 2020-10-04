package com.bookshop.controller;

import com.bookshop.controller.dto.BookDto;
import com.bookshop.entity.Book;
import com.bookshop.service.AuthorService;
import com.bookshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return bookService.getAllBooks();
        //return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
        //Optional<Book> book = bookService.getBookById(id);
        //return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
        //bookService.save(book);
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateBook(@RequestBody BookDto bookDto) {
            return bookService.update(bookDto);
//        bookService.update(book);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookService.delete(id);
        //bookService.delete(id);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO Delete an author from book?
}
