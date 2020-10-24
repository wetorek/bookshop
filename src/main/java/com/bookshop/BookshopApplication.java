package com.bookshop;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.BookDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.mapper.BookMapper;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.service.AuthorService;
import com.bookshop.service.BookService;
import com.bookshop.service.CategoryService;
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
    @Autowired
    CategoryService categoryService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        setUp();
//            updateBooksPatch();
//        updateBooksAddAuthorAndCategory();
//        updateBooksRemoveAuthorCategory();
        deleteBooks();
//        detachAuthor();
//        categories();
//        deleteCategory();


    }

    private void setUp() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        System.out.println(authorService.saveAuthor(authorDto1));
        System.out.println(authorService.saveAuthor(authorDto2));
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        System.out.println(categoryService.saveCategory(categoryDto1));
        System.out.println(categoryService.saveCategory(categoryDto2));
        BookDto book1 = new BookDto(11L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), List.of(authorDto1), List.of(categoryDto1));
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2));
        BookDto book3 = new BookDto(31L, new BigDecimal("23.22"), "Tomek u azjatów", LocalDate.of(2014, 1, 3), List.of(authorDto2), List.of(categoryDto2));
        System.out.println(bookService.save(book1));
        System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book3));
    }


    private void createBooksAndSave() {

    }


    private void deleteBooks() {
        System.out.println(bookService.delete(31L));
    }

    private void deleteCategory() {
        System.out.println(categoryService.deleteById(2L));
    }


    private void updateBooksPatch() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2));
        book2.setName("Changed name");
        System.out.println(bookService.update(book2));
    }


    private void updateBooksAddAuthorAndCategory() {

        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        BookDto book3 = new BookDto(31L, new BigDecimal("23.22"), "Tomek u azjatów", LocalDate.of(2014, 1, 3), List.of(authorDto1, authorDto2), List.of(categoryDto1, categoryDto2));
        System.out.println(bookService.update(book3));
    }

    private void updateBooksRemoveAuthorCategory() {
        AuthorDto authorDto1 = new AuthorDto(1L, "Puszek", "Wielki");
        AuthorDto authorDto2 = new AuthorDto(2L, "Jasiek", "Intellij");
        CategoryDto categoryDto1 = new CategoryDto(1L, "przygodowe");
        CategoryDto categoryDto2 = new CategoryDto(2L, "akcji");
        BookDto book2 = new BookDto(21L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1), List.of(categoryDto1));
        System.out.println(bookService.update(book2));
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
        BookDto book1 = new BookDto(11L, new BigDecimal("12.23"), "Tomek w krainie kangurów", LocalDate.of(1999, 12, 9), List.of(authorDto1));
        BookDto book2 = new BookDto(22L, new BigDecimal("21.37"), "Tomek na czarnym ladzie", LocalDate.of(2012, 2, 3), List.of(authorDto1, authorDto2));
        System.out.println(bookService.save(book2));
        System.out.println(bookService.save(book1));
        System.out.println(bookService.removeAuthorFromBook(22L, 1L));
    }*/

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

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper(new ModelMapper());
    }
}
