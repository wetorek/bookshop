package com.bookshop.mapper;

import com.bookshop.controller.dto.BookDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;


    public BookDto mapBookEntityToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book mapBookDtoToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public Book mapBookEntityToEntity(Book book, BookDto bookDto) {
        Book newBook = mapBookDtoToEntity(bookDto);
        List<Author> listOfAuthors = book.getAuthors();
        List<Category> listOfCategories = book.getCategories();
        List<Publisher> listOfPublishers = book.getPublishers();
        book.getPublishers().forEach(publisher -> publisher.getBooksPublisher().remove(book));
        book.getAuthors().forEach(u -> u.getBooksAuthor().remove(book));
        book.getCategories().forEach(u -> u.getBooksCategory().remove(book));
        book.setAuthors(new LinkedList<>());
        book.setCategories(new LinkedList<>());
        book.setPublishers(new LinkedList<>());
        listOfAuthors.forEach(author -> author.addBook(newBook));
        listOfCategories.forEach(category -> category.addBook(newBook));
        listOfPublishers.forEach(publisher -> publisher.addBook(newBook));
        return newBook;
    }
}
