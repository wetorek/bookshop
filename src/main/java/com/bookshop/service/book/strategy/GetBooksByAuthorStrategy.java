package com.bookshop.service.book.strategy;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetBooksByAuthorStrategy implements GetStrategy {
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks(Long id) {
        Author author = authorService.getAuthorById(id);
        return bookRepository.getBooksByAuthorsContains(author);
    }
}
