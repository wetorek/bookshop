package com.bookshop;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Publisher;
import com.bookshop.repository.AuthorRepository;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class BookshopApplication {

    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        Publisher publisher1 = new Publisher(1L, "State Street");
        Publisher publisher2 = new Publisher(2L, "Gordon Bank");
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);
        Author author1 = new Author(1L, "Jan", "Pawel");
        Author author2 = new Author(2L, "John", "Paul");
        Author author3 = new Author(3L, "Juanito", "Paulito");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        Book book1 = new Book(1L, new BigDecimal("12.23"), "Tomek w krainie kangurów", publisher1, List.of(author1));
        Book book2 = new Book(2L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", publisher2, List.of(author2, author3));
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}
