package com.bookshop.controller;

import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/book/author/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    @GetMapping("/book/author/{categoryId}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable Long categoryId) {
        return bookService.getBooksByCategory(categoryId);
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

    @PatchMapping("/patch/addAuthor/{bookId}/{authorId}")
    public ResponseEntity<Void> addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.addAuthorToBook(bookId, authorId);
    }

    @PatchMapping("/patch/addPublisher/{bookId}/{publisherId}")
    public ResponseEntity<Void> addPublisher(@PathVariable Long bookId, @PathVariable Long publisherId) {
        return bookService.addPublisherToBook(bookId, publisherId);
    }

    @PatchMapping("/patch/addCategory/{bookId}/{categoryId}")
    public ResponseEntity<Void> addCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        return bookService.addCategoryToBook(bookId, categoryId);
    }

    @PatchMapping("/patch/detachAuthor/{bookId}/{authorId}")
    public ResponseEntity<Void> removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.removeAuthorFromBook(bookId, authorId);
    }

    @PatchMapping("/patch/detachCategory/{bookId}/{categoryId}")
    public ResponseEntity<Void> removeCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        return bookService.removeCategoryFromBook(bookId, categoryId);
    }

    @PatchMapping("/patch/detachPublisher/{bookId}/{publisherId}")
    public ResponseEntity<Void> removePublisher(@PathVariable Long bookId, @PathVariable Long publisherId) {
        return bookService.removePublisherFromBook(bookId, publisherId);
    }
}
