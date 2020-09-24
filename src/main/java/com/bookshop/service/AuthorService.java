package com.bookshop.service;

import com.bookshop.entity.Author;
import com.bookshop.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void saveAuthor(Author author) {
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
}
