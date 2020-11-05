package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.BookDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;


    public BookDto mapBookEntityToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setAuthorDtoList(book.getAuthors().stream().map(author -> modelMapper.map(author, AuthorDto.class)).collect(Collectors.toList()));
        bookDto.setCategoryDtoList(book.getCategories().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList()));
        bookDto.setPublisherDtoList(book.getPublishers().stream().map(publisher -> modelMapper.map(publisher, PublisherDto.class)).collect(Collectors.toList()));
        return bookDto;
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
