package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.BookDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.mapper.BookMapper;
import com.bookshop.repository.AuthorRepository;
import com.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final AuthorService authorService;

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::mapBookEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> save(BookDto bookDto) {
        if (bookRepository.existsById(bookDto.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (!authorService.existAll(bookDto.getAuthorDtoList())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Author> authors = authorService.getAuthorsByList(bookDto.getAuthorDtoList());
        Book book = bookMapper.mapBookDtoToEntity(bookDto);
        authors.forEach(author -> author.addBook(book));
        bookRepository.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> update(BookDto bookDto) {
        if (!bookRepository.existsById(bookDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Book newBook = bookMapper.mapBookDtoToEntity(bookDto);
        if (compareBooksIfHaveTheSameAuthors(bookFromRepo, bookDto)) {
            List<Author> listOfAuthors = bookFromRepo.getAuthors();
            bookFromRepo.getAuthors().forEach(u -> u.getBooks().remove(bookFromRepo));
            bookFromRepo.setAuthors(new LinkedList<>());
            bookRepository.save(bookFromRepo);
            listOfAuthors.forEach(author -> author.addBook(bookFromRepo));
        } else {
            bookFromRepo.getAuthors().forEach(u -> u.getBooks().remove(bookFromRepo));
            bookFromRepo.setAuthors(new LinkedList<>());
            bookRepository.save(bookFromRepo);
            bookDto.getAuthorDtoList().stream().map(authorMapper::mapAuthorDtoToEntity).collect(Collectors.toList()).forEach(author -> author.addBook(newBook));
        }
        bookRepository.save(newBook);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean compareBooksIfHaveTheSameAuthors(Book book, BookDto bookDto) { // if ids are the same returns true
        List<Long> ids = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        List<Long> ids2 = bookDto.getAuthorDtoList().stream().map(AuthorDto::getId).collect(Collectors.toList());
        return ids.containsAll(ids2) && ids2.containsAll(ids);
    }

    @Transactional
    public ResponseEntity<Void> delete(Long id) {
        if (!bookRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        book.getAuthors().forEach(u -> u.getBooks().remove(book));
        book.setAuthors(new LinkedList<>());
        bookRepository.save(book);
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::mapBookEntityToDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
