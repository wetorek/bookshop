package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstName", source = "author.firstName")
    @Mapping(target = "secondName", source = "author.secondName")
    AuthorDto mapAuthorEntityToDto(Author author);

    Author mapAuthorDtoToEntity(AuthorDto authorDto);


}
