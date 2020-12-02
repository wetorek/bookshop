package com.bookshop.service.book.strategy;

import com.bookshop.entity.Book;
import com.bookshop.entity.Publisher;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetBooksByPublisherStrategy implements GetStrategy {
    private final PublisherService publisherService;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks(Long id) {
        Publisher publisher = publisherService.getPublisherById(id);
        return bookRepository.getBooksByPublishersContains(publisher);
    }
}
