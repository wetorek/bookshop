package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import com.bookshop.mapper.BookMapper;
import com.bookshop.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Slf4j
@AllArgsConstructor
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

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
        List<Publisher> publishers = publisherService.getPublishersByList(bookDto.getPublisherDtoList());
        publishers.forEach(publisher -> publisher.addBook(book));
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
            bookFromRepo.getPublishers().forEach(publisher -> publisher.getBooksPublisher().remove(bookFromRepo));
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
        bookDto.getPublisherDtoList().stream()
                .map(PublisherDto::getId)
                .map(publisherService::getPublisherEntity)
                .filter(Optional::isPresent).map(Optional::get)
                .forEach(bookFromRepo::addPublisher);
    }

    private boolean compareBooksIfHaveTheSameEntities(Book book, BookDto bookDto) { // if ids are the same returns true
        List<Long> authors = book.getAuthors().stream().map(Author::getId).collect(Collectors.toList());
        List<Long> authors2 = bookDto.getAuthorDtoList().stream().map(AuthorDto::getId).collect(Collectors.toList());
        List<Long> categories = book.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        List<Long> categories2 = bookDto.getCategoryDtoList().stream().map(CategoryDto::getId).collect(Collectors.toList());
        List<Long> publishers = bookDto.getPublisherDtoList().stream().map(PublisherDto::getId).collect(Collectors.toList());
        List<Long> publishers2 = book.getPublishers().stream().map(Publisher::getId).collect(Collectors.toList());
        return compareLists(authors, authors2) && compareLists(categories, categories2) && compareLists(publishers, publishers2);
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
        book.getCategories().forEach(u -> u.getBooksCategory().remove(book));
        book.getPublishers().forEach(publisher -> publisher.getBooksPublisher().remove(book));
        book.setAuthors(new LinkedList<>());
        book.setCategories(new LinkedList<>());
        book.setPublishers(new LinkedList<>());
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
    public ResponseEntity<Void> addCategoryToBook(Long bookId, Long categoryId) {
        if (!bookRepository.existsById(bookId) || categoryService.getCategoryEntity(categoryId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Category category = categoryService.getCategoryEntity(categoryId).orElseThrow(() -> new IllegalArgumentException("This category does not exist in repo"));
        bookFromRepo.addCategory(category);
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

    @Transactional
    public ResponseEntity<Void> removeCategoryFromBook(Long bookId, Long categoryId) {
        if (!bookRepository.existsById(bookId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        if (bookFromRepo.getCategories().stream().map(Category::getId).noneMatch(u -> u.equals(categoryId))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category category = categoryService.getCategoryEntity(categoryId).orElseThrow();
        bookFromRepo.removeCategory(category);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<Void> removePublisherFromBook(Long bookId, Long publisherId) {
        if (!bookRepository.existsById(bookId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        if (bookFromRepo.getPublishers().stream().map(Publisher::getId).noneMatch(u -> u.equals(publisherId))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Publisher publisher = publisherService.getPublisherEntity(publisherId).orElseThrow();
        bookFromRepo.removePublisher(publisher);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<Void> addPublisherToBook(Long bookId, Long publisherId) {
        if (!bookRepository.existsById(bookId) || publisherService.getPublisherEntity(publisherId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Publisher publisher = publisherService.getPublisherEntity(publisherId).orElseThrow();
        bookFromRepo.addPublisher(publisher);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(Long authorID) {
        Optional<Author> author = authorService.getAuthorEntity(authorID);
        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> bookList = bookRepository.getBooksByAuthorsContains(author.get());
        List<BookDto> bookDtoList = bookMapper.mapListOfEntitiesToDto(bookList);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BookDto>> getBooksByCategory(Long categoryId) {
        Optional<Category> category = categoryService.getCategoryEntity(categoryId);
        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> bookList = bookRepository.getBooksByCategoriesContains(category.get());
        List<BookDto> bookDtoList = bookMapper.mapListOfEntitiesToDto(bookList);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    Optional<Book> getBookByID(Long id) {
        return bookRepository.findById(id);
    }
}
