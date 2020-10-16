package com.bookshop;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.BookDto;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.mapper.BookMapper;
import com.bookshop.service.AuthorService;
import com.bookshop.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BookshopApplication {

    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

//        createBooksAndSave();
//        updateBooks();
//        updateBooksAddAuthor();
        updateBooksRemoveAuthor();


        // System.out.println(bookService.save(book1));
        /*System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book3));*/
        /*Publisher publisher1 = new Publisher(1L, "State Street");
        Publisher publisher2 = new Publisher(2L, "Gordon Bank");
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);*/
        /*Author author1 = new Author(1L, "Jan", "Pawel", null);
        Author author2 = new Author(2L, "John", "Paul", null);
        Author author3 = new Author(3L, "Juanito", "Paulito", null);
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);*/

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

    private void createBooksAndSave() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));
        BookDto book1 = new BookDto(99L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), List.of(authorDto1));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        BookDto book3 = new BookDto(31L, new BigDecimal("23.22"), "Tomek u azjatów", LocalDate.of(2014, 1, 3), List.of(authorDto2));
        System.out.println(bookService.save(book1));
        System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book3));
    }

    private void updateBooks() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        System.out.println(bookService.save(book2));
        book2.setName("Changed name");
        System.out.println(bookService.update(book2));
    }
    private void updateBooksAddAuthor() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1));
        System.out.println(bookService.save(book2));
        BookDto book3 = new BookDto(21L, new BigDecimal("9999.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        System.out.println(bookService.update(book3));
    }

    private void updateBooksRemoveAuthor() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        System.out.println(bookService.save(book2));
        BookDto book3 = new BookDto(21L, new BigDecimal("9999.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto2));
        System.out.println(bookService.update(book3));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public AuthorMapper authorMapper() {
        return new AuthorMapper(new ModelMapper());
    }

    @Bean
    public BookMapper bookMapper() {
        return new BookMapper(new ModelMapper());
    }
}
