package com.bookshop.service;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.ApplicationNotFoundException;
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
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(BookDto bookDto) {
        if (bookRepository.existsById(bookDto.getId())) {
            throw new ApplicationConflictException("Book already exists: " + bookDto);
        }
        if (!checkIfAllExist(bookDto)) {
            throw new ApplicationNotFoundException("Some of the mapped entities does not exist: " + bookDto);
        }
        Book book = createNewBok(bookDto);
        bookRepository.save(book);
        logger.info("Book created: " + bookDto.getId());
    }

    private boolean checkIfAllExist(BookDto bookDto) {
        return authorService.existAll(bookDto.getAuthorDtoList()) && categoryService.existAll(bookDto.getCategoryDtoList()) && publisherService.existAll(bookDto.getPublisherDtoList());
    }

    private Book createNewBok(BookDto bookDto) {
        Book book = bookMapper.mapBookDtoToEntity(bookDto);
        book.setAuthors(authorService.getAuthorsByList(bookDto.getAuthorDtoList()));
        book.setCategories(categoryService.getCategoriesByList(bookDto.getCategoryDtoList()));
        book.setPublishers(publisherService.getPublishersByList(bookDto.getPublisherDtoList()));
        return book;
    }

    @Transactional
    public void update(BookDto bookDto) {
        if (!bookRepository.existsById(bookDto.getId())) {
            throw new ApplicationNotFoundException("Book not found: " + bookDto);
        }
        Book bookFromRepo = getBookById(bookDto.getId()); //todo refactor this lad
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
    }

    private void attachEntitiesToBook(Book bookFromRepo, BookDto bookDto) {
        bookDto.getAuthorDtoList().stream()
                .map(author -> authorService.getAuthorById(author.getId()))
                .forEach(bookFromRepo::addAuthor);
        bookDto.getCategoryDtoList().stream()
                .map(category -> categoryService.getCategoryById(category.getId()))
                .forEach(bookFromRepo::addCategory);
        bookDto.getPublisherDtoList().stream()
                .map(PublisherDto::getId)
                .map(publisherService::getPublisherById)
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
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Book not found " + id));
    }

    @Transactional
    public ResponseEntity<Void> addAuthorToBook(Long bookId, Long authorId) {
        if (!bookRepository.existsById(bookId) || authorService.getAuthorById(authorId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Author author = authorService.getAuthorById(authorId);
        bookFromRepo.addAuthor(author);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<Void> addCategoryToBook(Long bookId, Long categoryId) {
        if (!bookRepository.existsById(bookId) || categoryService.getCategoryById(categoryId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Category category = categoryService.getCategoryById(categoryId);
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
        Category category = categoryService.getCategoryById(categoryId);
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
        Publisher publisher = publisherService.getPublisherById(publisherId);
        bookFromRepo.removePublisher(publisher);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<Void> addPublisherToBook(Long bookId, Long publisherId) {
        if (!bookRepository.existsById(bookId) || publisherService.getPublisherById(publisherId) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Publisher publisher = publisherService.getPublisherById(publisherId);
        bookFromRepo.addPublisher(publisher);
        bookRepository.save(bookFromRepo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(Long authorID) {
        Author author = authorService.getAuthorById(authorID);
        List<Book> bookList = bookRepository.getBooksByAuthorsContains(author);
        List<BookDto> bookDtoList = bookMapper.mapListOfEntitiesToDto(bookList);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BookDto>> getBooksByCategory(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Book> bookList = bookRepository.getBooksByCategoriesContains(category);
        List<BookDto> bookDtoList = bookMapper.mapListOfEntitiesToDto(bookList);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    public boolean doesBookExist(Long id) {
        return bookRepository.existsById(id);
    }
}
