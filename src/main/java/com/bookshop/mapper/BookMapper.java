package com.bookshop.mapper;

import com.bookshop.controller.dto.BookDto;
import com.bookshop.entity.Book;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;


    public BookDto mapBookEntityToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book mapBookDtoToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
