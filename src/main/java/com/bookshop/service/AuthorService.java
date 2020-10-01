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
}
    /*

    @Transactional
    public void saveAuthor(AuthorDto authorDto) {

        authorRepository.save(author);
    }

    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}*/
