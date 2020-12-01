package com.bookshop.mapper;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthorMapper {
    private final ModelMapper modelMapper;

    public AuthorDto mapAuthorEntityToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    public Author mapAuthorDtoToEntity(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }

    public Author mapAuthorDtoUsingEntity(AuthorDto authorDto, Author author) {
        Author mappedAuthor = mapAuthorDtoToEntity(authorDto);
        List<Book> books = author.getBooksAuthor();
        author.getBooksAuthor().forEach(book -> book.getAuthors().remove(author));
        author.setBooksAuthor(new LinkedList<>());
        books.forEach(book -> book.addAuthor(mappedAuthor));
        return mappedAuthor;
    }

    public List<AuthorDto> mapListOfAuthorsToDto(List<Author> authors) {
        return authors
                .stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }
}
