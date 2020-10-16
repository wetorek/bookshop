package com.bookshop.mapper;

import com.bookshop.controller.dto.BookDto;
import com.bookshop.entity.Book;
import com.bookshop.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;
//    private final AuthorRepository authorRepository;


    public BookDto mapBookEntityToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book mapBookDtoToEntity(BookDto bookDto, AuthorRepository authorRepository) {
        Book book = modelMapper.map(bookDto, Book.class);
        book.setAuthors(bookDto.getAuthorDtoList()
                .stream()
                .map(u -> authorRepository.findById(u.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
        return book;
    }

    public Book mapBookDtoToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
