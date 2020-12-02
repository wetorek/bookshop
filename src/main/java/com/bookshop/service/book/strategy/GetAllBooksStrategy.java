package com.bookshop.service.book.strategy;

import com.bookshop.entity.Book;
import com.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAllBooksStrategy implements GetStrategy {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks(Long id) {
        return bookRepository.findAll();
    }
}
