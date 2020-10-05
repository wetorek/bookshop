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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::mapBookEntityToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> save(BookDto bookDto) {
        if (!bookRepository.existsById(bookDto.getId()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        bookRepository.save(bookMapper.mapBookDtoToEntity(bookDto));
        createAuthorIfDoesntExist(bookDto.getAuthorDtoList());
        bookDto.getAuthorDtoList().forEach(u -> {
            Optional<Author> authorFromRepo = authorRepository.findById(u.getId());
            if (authorFromRepo.isPresent()) {
                List<Book> booksFromAuthor = authorFromRepo.get().getBooks();
                booksFromAuthor.add(bookMapper.mapBookDtoToEntity(bookDto));
                authorFromRepo.get().setBooks(booksFromAuthor);
                authorRepository.save(authorFromRepo.get());

            }
            //authorFromRepo.ifPresent( ->getBooks() );
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void createAuthorIfDoesntExist(List<AuthorDto> authorDto) {
        authorDto.stream()
                .filter(u -> !authorRepository.existsById(u.getId()))
                .forEach(u -> authorRepository.save(authorMapper.mapAuthorDtoToEntity(u)));
    }

    public void update(BookDto bookDto) {
        bookRepository.save(bookDto);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public ResponseEntity<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::mapBookEntityToDto)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
