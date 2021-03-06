package com.bookshop;

import com.bookshop.config.SwaggerConfiguration;
import com.bookshop.controller.AuthorController;
import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.service.AdditionalServicesService;
import com.bookshop.service.CategoryService;
import com.bookshop.service.PublisherService;
import com.bookshop.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EnableAsync
@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class BookshopApplication {

    @Autowired
    AuthorController authorController;
    @Autowired
    BookService bookService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PublisherService publisherService;
    @Autowired
    AdditionalServicesService additionalServicesService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
//        publisherTest();
        setUp();
//        updateBooksPatch();
//        updateBooksAddConnectedEntities();
//        updateBooksRemoveAuthorCategory();
//        deleteBooks();
//        detachAuthor();
//        categories();
//        deleteCategory();

    }

    private void setUp() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        authorController.addAuthor(authorDto1);
        authorController.addAuthor(authorDto2);
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        categoryService.saveCategory(categoryDto1);
        categoryService.saveCategory(categoryDto2);
        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        publisherService.savePublisher(publisherDto1);
        publisherService.savePublisher(publisherDto2);
        BookDto book1 = new BookDto(11L, new BigDecimal("12.23"), "Tomek w krainie kangurów", 111L, LocalDate.of(1999, 12, 9), List.of(authorDto1), List.of(categoryDto1), List.of(publisherDto1));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", 1111L, LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2), List.of(publisherDto1, publisherDto2));
        BookDto book3 = new BookDto(31L, new BigDecimal("23.22"), "Tomek u azjatów", 111L, LocalDate.of(2014, 1, 3), List.of(authorDto2), List.of(categoryDto2), List.of(publisherDto2));
        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        /*AdditionalServiceDto additionalServiceDto1 = new AdditionalServiceDto(1L, BigDecimal.TEN, "service 1");
        AdditionalServiceDto additionalServiceDto2 = new AdditionalServiceDto(2L, BigDecimal.ONE, "service 2");
        additionalServicesService.addService(additionalServiceDto1);
        additionalServicesService.addService(additionalServiceDto2);*/
//        System.out.println(bookService.getAllBooks());

    }


    private void publisherTest() {
        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        publisherService.savePublisher(publisherDto1);
        publisherService.savePublisher(publisherDto2);
        System.out.println(publisherService.getAllPublishers());
        publisherDto2.setName("dupa");
        publisherService.updatePublisher(publisherDto2);
        System.out.println(publisherService.getAllPublishers());
        publisherService.deletePublisher(2L);
        System.out.println(publisherService.getPublisherById(3L));

    }


    private void deleteBooks() {
        bookService.delete(31L);
    }


    private void updateBooksPatch() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", 1111L, LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2), List.of(publisherDto1, publisherDto2));
        book2.setName("Changed name");
        bookService.update(book2);
    }


    private void updateBooksAddConnectedEntities() {

        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        BookDto book3 = new BookDto(31L, new BigDecimal("23.22"), "Tomek u azjatów", 111L, LocalDate.of(2014, 1, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2), List.of(publisherDto1, publisherDto2));
        bookService.update(book3);
    }

    private void updateBooksRemoveAuthorCategory() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", 1111L, LocalDate.of(2012, 2, 3), List.of(authorDto1), List.of(categoryDto1), List.of(publisherDto1));
        bookService.update(book2);
    }
    /*
    private void attachAuthor() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));

        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        System.out.println(categoryService.saveCategory(categoryDto1));
        System.out.println(categoryService.saveCategory(categoryDto2));

        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        BookDto book1 = new BookDto(11L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), List.of(authorDto1));
        BookDto book2 = new BookDto(22L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto2));
        System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book1));
        System.out.println(bookService.addAuthorToBook(11L, 2L));
    }

    private void detachAuthor() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));

        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        System.out.println(categoryService.saveCategory(categoryDto1));
        System.out.println(categoryService.saveCategory(categoryDto2));

        PublisherDto publisherDto1 = new PublisherDto(1L, "Gazeta wyborcza");
        PublisherDto publisherDto2 = new PublisherDto(2L, "Dziennik codzienny");
        BookDto book1 = new BookDto(11L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), List.of(authorDto1));
        BookDto book2 = new BookDto(22L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book1));
        System.out.println(bookService.removeAuthorFromBook(22L, 1L));
    }*/


}
