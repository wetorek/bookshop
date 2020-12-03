package com.bookshop.controller;

import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Book;
import com.bookshop.mapper.BookMapper;
import com.bookshop.service.VoteService;
import com.bookshop.service.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final VoteService voteService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return bookMapper.mapBookEntityToDto(book);
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
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksBy(@RequestParam(required = false) Long authorId, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long publisherId) {
        List<Book> books = bookService.getBooksByParams(authorId, categoryId, publisherId);

        return bookMapper.mapListOfEntitiesToDto(books);
    }

    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return bookMapper.mapListOfEntitiesToDto(books);
    }*/

    /*@GetMapping("/asdasdasd")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByAuthor(@RequestParam() Long authorId) {
        List<Book> books = bookService.getBooksByAuthor(authorId);
        return bookMapper.mapListOfEntitiesToDto(books);
    }

    @GetMapping("/book/author/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByCategory(@PathVariable Long categoryId) {
        List<Book> books = bookService.getBooksByCategory(categoryId);
        return bookMapper.mapListOfEntitiesToDto(books);
    }*/

    @PatchMapping("addAuthor/{bookId}/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.addAuthorToBook(bookId, authorId);
    }

    @PatchMapping("addPublisher/{bookId}/{publisherId}")
    @ResponseStatus(HttpStatus.OK)
    public void addPublisher(@PathVariable Long bookId, @PathVariable Long publisherId) {
        bookService.addPublisherToBook(bookId, publisherId);
    }

    @PatchMapping("addCategory/{bookId}/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void addCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        bookService.addCategoryToBook(bookId, categoryId);
    }

    @PatchMapping("detachAuthor/{bookId}/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.removeAuthorFromBook(bookId, authorId);
    }

    @PatchMapping("detachCategory/{bookId}/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        bookService.removeCategoryFromBook(bookId, categoryId);
    }

    @PatchMapping("detachPublisher/{bookId}/{publisherId}")
    @ResponseStatus(HttpStatus.OK)
    public void removePublisher(@PathVariable Long bookId, @PathVariable Long publisherId) {
        bookService.removePublisherFromBook(bookId, publisherId);
    }
}
