package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Book;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

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


    public List<BookDto> mapListOfEntitiesToDto(List<Book> bookList) {
        return bookList
                .stream()
                .map(this::mapBookEntityToDto)
                .collect(Collectors.toList());
    }
}
