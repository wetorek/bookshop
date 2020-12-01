package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.ApplicationNotFoundException;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Author not found " + id));
    }

    @Transactional
    public void saveAuthor(AuthorDto authorDto) {
        if (authorRepository.existsById(authorDto.getId())) {
            throw new ApplicationConflictException("Author already exists: " + authorDto);
        }
        authorRepository.save(authorMapper.mapAuthorDtoToEntity(authorDto));
    }

    @Transactional     //TODO change it using cascade
    public void updateAuthor(AuthorDto authorDto) {
        if (!authorRepository.existsById(authorDto.getId())) {
            throw new ApplicationNotFoundException("Author not found: " + authorDto);
        }
        Author authorFromRepo = getAuthorById(authorDto.getId());
        Author newAuthor = authorMapper.mapAuthorDtoUsingEntity(authorDto, authorFromRepo);
        authorRepository.save(newAuthor);
    }

    @Transactional           //TODO change it using cascade
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return;
        }
        Author author = getAuthorById(id);
        author.getBooksAuthor().forEach(book -> book.removeAuthor(author));
        authorRepository.save(author);
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsByList(List<AuthorDto> authorDto) {
        return authorDto.stream()
                .map(u -> authorRepository.findById(u.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean existAll(List<AuthorDto> authorDtoList) {
        return authorDtoList.stream()
                .allMatch(u -> authorRepository.existsById(u.getId()));
    }
}
