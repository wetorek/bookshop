package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class AuthorMapper {
    private final ModelMapper modelMapper;

    public AuthorDto mapAuthorEntityToDto ( Author author){
        AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
        return authorDto;
    }
    public Author mapAuthorDtoToEntity( AuthorDto authorDto){
        Author author = modelMapper.map(authorDto, Author.class);
        return author;
    }
}
