package com.bookshop;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import com.bookshop.repository.AuthorRepository;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.CategoryRepository;
import com.bookshop.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BookshopApplication {

    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        /*Publisher publisher1 = new Publisher(1L, "State Street");
        Publisher publisher2 = new Publisher(2L, "Gordon Bank");
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);*/
        Author author1 = new Author(1L, "Jan", "Pawel");
        Author author2 = new Author(2L, "John", "Paul");
        Author author3 = new Author(3L, "Juanito", "Paulito");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        /*Category category1 = new Category(1L, "Przygodowe");
        Category category2 = new Category(2L, "Akcji");
        Category category3 = new Category(3L, "Kryminał");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        Book book1 = new Book(1L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), publisher1, List.of(author1), category1);
        Book book2 = new Book(2L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), publisher2, List.of(author2, author3), category2);
        Book book3 = new Book(3L, new BigDecimal("23.22"), "Tomek u murzymnów", LocalDate.of(2014, 1, 3), publisher2, List.of(author3), category3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);*/
    }
}
