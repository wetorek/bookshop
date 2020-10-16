package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
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
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<AuthorDto> getAuthorById(Long id) {
        Optional<AuthorDto> authorDto = authorRepository.findById(id)
                .map(authorMapper::mapAuthorEntityToDto);
        return authorDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<Void> saveAuthor(AuthorDto authorDto) {
        if (authorRepository.existsById(authorDto.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        authorRepository.save(authorMapper.mapAuthorDtoToEntity(authorDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> updateAuthor(AuthorDto authorDto) {
        if (!authorRepository.existsById(authorDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorRepository.save(authorMapper.mapAuthorDtoToEntity(authorDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Author author = authorRepository.findById(id).orElseThrow();
        author.getBooksAuthor().forEach(book -> book.removeAuthor(author));
        authorRepository.save(author);
        authorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<Author> getAuthorsByList(List<AuthorDto> authorDto) {
        return authorDto.stream()
                .map(u -> authorRepository.findById(u.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean existAll(List<AuthorDto> authorDtos) {
        return authorDtos.stream()
                .allMatch(u -> authorRepository.existsById(u.getId()));
    }

    public Optional<Author> getAuthorEntity (Long id){
        return authorRepository.findById(id);
    }


}
