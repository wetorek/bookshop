package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class AuthorMapper {
    private final ModelMapper modelMapper;

    public AuthorDto mapAuthorEntityToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    public Author mapAuthorDtoToEntity(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
