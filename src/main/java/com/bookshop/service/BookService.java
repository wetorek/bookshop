package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.BookDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.mapper.BookMapper;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;
    private final AuthorService authorService;
    private final CategoryService categoryService;

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
        if (!authorService.existAll(bookDto.getAuthorDtoList()) || !categoryService.existAll(bookDto.getCategoryDtoList())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookRepository.save(matchBookWithEntities(bookDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Book matchBookWithEntities(BookDto bookDto) {
        List<Author> authors = authorService.getAuthorsByList(bookDto.getAuthorDtoList());
        Book book = bookMapper.mapBookDtoToEntity(bookDto);
        authors.forEach(author -> author.addBook(book));
        List<Category> categories = categoryService.getCategoriesByList(bookDto.getCategoryDtoList());
        categories.forEach(category -> category.addBook(book));
        return book;
    }

    @Transactional
    public ResponseEntity<Void> update(BookDto bookDto) {
        if (!bookRepository.existsById(bookDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        if (compareBooksIfHaveTheSameEntities(bookFromRepo, bookDto)) {
            Book newBook = bookMapper.mapBookEntityToEntity(bookFromRepo, bookDto);
            bookRepository.save(bookFromRepo);
            bookRepository.save(newBook);
        } else {
            bookFromRepo.getAuthors().forEach(author -> author.getBooksAuthor().remove(bookFromRepo));
            bookFromRepo.getCategories().forEach(category -> category.getBooksCategory().remove(bookFromRepo));
            attachEntitiesToBook(bookFromRepo, bookDto);
            bookRepository.save(bookFromRepo);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void attachEntitiesToBook(Book bookFromRepo, BookDto bookDto) {
        bookDto.getAuthorDtoList().stream()
                .map(author -> authorService.getAuthorEntity(author.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(bookFromRepo::addAuthor);
        bookDto.getCategoryDtoList().stream()
                .map(category -> categoryService.getCategoryEntity(category.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(bookFromRepo::addCategory);
    }

    private boolean compareBooksIfHaveTheSameEntities(Book book, BookDto bookDto) { // if ids are the same returns true
        List<Long> authors = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        List<Long> authors2 = bookDto.getAuthorDtoList().stream().map(AuthorDto::getId).collect(Collectors.toList());
        List<Long> categories = book.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        List<Long> categories2 = bookDto.getCategoryDtoList().stream().map(CategoryDto::getId).collect(Collectors.toList());
        return compareLists(authors, authors2) && compareLists(categories, categories2);
    }

    private boolean compareLists(List<Long> list1, List<Long> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
    }

    @Transactional
    public ResponseEntity<Void> delete(Long id) {
        if (!bookRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        book.getAuthors().forEach(u -> u.getBooksAuthor().remove(book));
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

    @Transactional
    public ResponseEntity<Void> addAuthorToBook(Long bookId, Long authorId) {
        if (!bookRepository.existsById(bookId) || authorService.getAuthorEntity(authorId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Author author = authorService.getAuthorEntity(authorId).orElseThrow(() -> new IllegalArgumentException("This author does not exist in repo"));
        bookFromRepo.addAuthor(author);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<Void> removeAuthorFromBook(Long bookId, Long authorId) {
        if (!bookRepository.existsById(bookId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        if (bookFromRepo.getAuthors().stream().map(Author::getId).noneMatch(u -> u.equals(authorId))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Author author = bookFromRepo.getAuthors().stream().filter(u -> u.getId().equals(authorId)).findFirst().orElseThrow();
        bookFromRepo.removeAuthor(author);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
