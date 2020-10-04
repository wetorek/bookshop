package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.repository.AuthorRepository;
import com.bookshop.repository.BookRepository;
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
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::mapAuthorEntityToDto);
    }

    @Transactional
    public ResponseEntity<Void> saveAuthor(AuthorDto authorDto) {
        authorRepository.save(authorMapper.mapAuthorDtoToEntity(authorDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Void> updateAuthor(AuthorDto authorDto) {
        if (!authorRepository.existsById(authorDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorRepository.save(authorMapper.mapAuthorDtoToEntity(authorDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorRepository.deleteById(id); //TODO do sth with his books
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
