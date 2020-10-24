package com.bookshop.controller;

import com.bookshop.controller.dto.BookDto;
import com.bookshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateBook(@RequestBody BookDto bookDto) {
        return bookService.update(bookDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookService.delete(id);
    }

    @PatchMapping("/patch/add/{bookId}/{authorId}")
    public ResponseEntity<Void> addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.addAuthorToBook(bookId, authorId);
    }

    @PatchMapping("/patch/remove/{bookId}/{authorId}")
    public ResponseEntity<Void> removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.removeAuthorFromBook(bookId, authorId);
    }
}
