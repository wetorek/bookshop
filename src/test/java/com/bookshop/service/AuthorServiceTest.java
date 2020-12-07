package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.ApplicationNotFoundException;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;
    @Mock
    AuthorMapper authorMapper;

    @InjectMocks
    AuthorService authorService;

    @Test
    void shouldReturnAllAuthors() {
        //given
        List<Author> authors = List.of(Mockito.mock(Author.class), Mockito.mock(Author.class));
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        //when
        List<Author> result = authorService.getAllAuthors();

        //then
        Assertions.assertEquals(2, result.size());
        Mockito.verify(authorRepository, Mockito.only()).findAll();
    }

    @Test
    void shouldReturnEmptyListOfAuthors() {
        //given
        Mockito.when(authorRepository.findAll()).thenReturn(new LinkedList<>());

        //when
        List<Author> result = authorService.getAllAuthors();

        //then
        Assertions.assertEquals(0, result.size());
        Mockito.verify(authorRepository, Mockito.only()).findAll();
    }

    @Test
    void shouldSafeNewAuthor() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "author", "surname");
        Mockito.when(authorRepository.existsById(1L)).thenReturn(false);
        Author author = Mockito.mock(Author.class);
        Mockito.when(authorMapper.mapAuthorDtoToEntity(authorDto)).thenReturn(author);

        //when
        authorService.saveAuthor(authorDto);

        //then
        Mockito.verify(authorMapper, Mockito.only()).mapAuthorDtoToEntity(authorDto);
        Mockito.verify(authorRepository, Mockito.times(1)).save(author);
    }

    @Test
    void shouldThrowExceptionWhenSavingAuthor() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "author", "surname");
        Mockito.when(authorRepository.existsById(1L)).thenReturn(true);

        //when
        var exception = assertThrows(ApplicationConflictException.class, () -> authorService.saveAuthor(authorDto));

        //then
        assertTrue(exception.getMessage().contains(authorDto.toString()));
        Mockito.verify(authorMapper, Mockito.never()).mapAuthorDtoToEntity(Mockito.any());
        Mockito.verify(authorRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldUpdateExistingAuthor() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "author", "surname");
        Mockito.when(authorRepository.existsById(authorDto.getId())).thenReturn(true);
        Author author = Mockito.mock(Author.class);
        Mockito.when(authorRepository.findById(authorDto.getId())).thenReturn(Optional.of(author));
        Author newAuthor = Mockito.mock(Author.class);
        Mockito.when(authorMapper.mapAuthorDtoUsingEntity(authorDto, author)).thenReturn(newAuthor);

        //when
        authorService.updateAuthor(authorDto);

        //then
        Mockito.verify(authorRepository, Mockito.times(1)).findById(authorDto.getId());
        Mockito.verify(authorMapper, Mockito.times(1)).mapAuthorDtoUsingEntity(authorDto, author);
        Mockito.verify(authorRepository, Mockito.times(1)).save(newAuthor);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAuthor() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "author", "surname");
        Mockito.when(authorRepository.existsById(authorDto.getId())).thenReturn(false);

        //when
        var exception = assertThrows(ApplicationNotFoundException.class, () -> authorService.updateAuthor(authorDto));

        //then
        assertTrue(exception.getMessage().contains(authorDto.toString()));
        Mockito.verify(authorRepository, Mockito.only()).existsById(authorDto.getId());
    }

    @Test
    void deleteExistingAuthor() {
        //given
        Long authorId = 1L;
        Mockito.when(authorRepository.existsById(authorId)).thenReturn(true);

        //when
        authorService.deleteAuthor(authorId);

        //then
        Mockito.verify(authorRepository, Mockito.times(1)).existsById(authorId);
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(authorId);
    }

    @Test
    void deleteNotExistingAuthor() {
        //given
        Long authorId = 1L;
        Mockito.when(authorRepository.existsById(authorId)).thenReturn(false);

        //when
        var exception = assertThrows(ApplicationNotFoundException.class, () -> authorService.deleteAuthor(authorId));

        //then
        Mockito.verify(authorRepository, Mockito.only()).existsById(authorId);
        assertTrue(exception.getMessage().contains(authorId.toString()));
    }

    @Test
    void getAuthorsByListTestEmptyList() {
        //given
        List<AuthorDto> authorDtoList = new LinkedList<>();

        //when
        List<Author> authors = authorService.getAuthorsByList(authorDtoList);

        //then
        assertTrue(authors.isEmpty());
    }

    @Test
    void getAuthorsByListTestTwoElements() {
        //given
        Author author1 = Mockito.mock(Author.class);
        Author author2 = Mockito.mock(Author.class);
        AuthorDto authorDto1 = new AuthorDto(1L, "author1", "surname1");
        AuthorDto authorDto2 = new AuthorDto(2L, "author2", "surname2");
        List<AuthorDto> authorDtoList = List.of(authorDto1, authorDto2);
        Mockito.when(authorRepository.findById(authorDto1.getId())).thenReturn(Optional.of(author1));
        Mockito.when(authorRepository.findById(authorDto2.getId())).thenReturn(Optional.of(author2));

        //when
        List<Author> authors = authorService.getAuthorsByList(authorDtoList);

        //then
        assertEquals(2, authors.size());
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
        Mockito.verify(authorRepository, Mockito.times(2)).findById(Mockito.any());
    }

    @Test
    void getAuthorsByListAuthorDoesNotExist() {
        //given
        AuthorDto authorDto1 = new AuthorDto(1L, "author1", "surname1");
        List<AuthorDto> authorDtoList = List.of(authorDto1);
        Mockito.when(authorRepository.findById(authorDto1.getId())).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(ApplicationNotFoundException.class, () -> authorService.getAuthorsByList(authorDtoList));

        //then
        assertTrue(exception.getMessage().contains(authorDto1.getId().toString()));
    }

    @Test
    void existAuthorsWhichAllExists() {
        //given
        AuthorDto authorDto1 = new AuthorDto(1L, "author1", "surname1");
        AuthorDto authorDto2 = new AuthorDto(2L, "author2", "surname2");
        List<AuthorDto> authorDtoList = List.of(authorDto1, authorDto2);
        Mockito.when(authorRepository.existsById(authorDto1.getId())).thenReturn(true);
        Mockito.when(authorRepository.existsById(authorDto2.getId())).thenReturn(true);

        //when
        boolean result = authorService.existAll(authorDtoList);

        //then
        assertTrue(result);
    }

    @Test
    void existAuthorsWhichOneNotExist() {
        //given
        AuthorDto authorDto1 = Mockito.mock(AuthorDto.class);
        AuthorDto authorDto2 = Mockito.mock(AuthorDto.class);
        List<AuthorDto> authorDtoList = List.of(authorDto1, authorDto2);
        Mockito.when(authorRepository.existsById(authorDto1.getId())).thenReturn(true);
        Mockito.when(authorRepository.existsById(authorDto2.getId())).thenReturn(false);
        //when
        boolean result = authorService.existAll(authorDtoList);

        //then
        assertFalse(result);
    }

    @Test
    void getAuthorExistsTest(){
        //given
        Author author = Mockito.mock(Author.class);
        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        //when
        Author result = authorService.getAuthorById(author.getId());

        //then
        assertEquals(author, result);
        Mockito.verify(authorRepository, Mockito.only()).findById(author.getId());
    }

    @Test
    void getAuthorDoesNotExist(){
        //given
        Author author = Mockito.mock(Author.class);
        Mockito.when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());

        //when
        var exception = assertThrows(ApplicationNotFoundException.class, () -> authorService.getAuthorById(author.getId()));

        //then
        assertTrue(exception.getMessage().contains(author.getId().toString()));
        Mockito.verify(authorRepository, Mockito.only()).findById(author.getId());
    }
}