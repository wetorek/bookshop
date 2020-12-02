package com.bookshop.controller;

import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Book;
import com.bookshop.mapper.BookMapper;
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

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return bookMapper.mapListOfEntitiesToDto(books);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return bookMapper.mapBookEntityToDto(book);
    }

    @GetMapping("/asdasdasd")   //todo solve this problem
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@RequestParam() Long authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    @GetMapping("/book/author/{categoryId}")
    public ResponseEntity<List<BookDto>> getBooksByCategory(@PathVariable Long categoryId) {
        return bookService.getBooksByCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookDto bookDto) {
        bookService.save(bookDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody BookDto bookDto) {
        bookService.update(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
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
