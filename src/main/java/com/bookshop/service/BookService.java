package com.bookshop.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.bookshop.util.BookServiceUtils.*;

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
        Book bookFromRepo = getBookById(bookDto.getId());
        List<Publisher> publisherList = bookFromRepo.getPublishers();
        List<Category> categoryList = bookFromRepo.getCategories();
        List<Author> authorList = bookFromRepo.getAuthors();
        dispatchEntitiesFromBook(bookFromRepo);
        Book newBook = bookMapper.mapBookDtoToEntity(bookDto);
        if (compareBooksIfHaveTheSameEntities(bookFromRepo, bookDto)) {
            newBook.setAuthors(authorList);
            newBook.setCategories(categoryList);
            newBook.setPublishers(publisherList);
        } else {
            newBook.setAuthors(authorService.getAuthorsByList(bookDto.getAuthorDtoList()));
            newBook.setCategories(categoryService.getCategoriesByList(bookDto.getCategoryDtoList()));
            newBook.setPublishers(publisherService.getPublishersByList(bookDto.getPublisherDtoList()));
        }
        bookRepository.save(newBook);
    }

    private void dispatchEntitiesFromBook(Book bookFromRepo) {
        bookFromRepo.getAuthors().forEach(author -> author.getBooksAuthor().remove(bookFromRepo));
        bookFromRepo.getCategories().forEach(category -> category.getBooksCategory().remove(bookFromRepo));
        bookFromRepo.getPublishers().forEach(publisher -> publisher.getBooksPublisher().remove(bookFromRepo));
    }

    private boolean compareBooksIfHaveTheSameEntities(Book book, BookDto bookDto) {
        return compareAuthors(book, bookDto) && comparePublishers(book, bookDto) && compareCategories(book, bookDto);
    }


    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ApplicationNotFoundException("Book not found: " + id);
        }
        Book book = getBookById(id);
        dispatchEntitiesFromBook(book);
        bookRepository.save(book);
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Book not found " + id));
    }

    @Transactional
    public void addAuthorToBook(Long bookId, Long authorId) {
        if (!bookRepository.existsById(bookId) || authorService.getAuthorById(authorId) == null) {
            throw new ApplicationNotFoundException("Book not found " + bookId + " author " + authorId);
        }
        Book bookFromRepo = getBookById(bookId);
        Author author = authorService.getAuthorById(authorId);
        bookFromRepo.addAuthor(author);
        bookRepository.save(bookFromRepo);
    }

    @Transactional
    public void addCategoryToBook(Long bookId, Long categoryId) {
        if (!bookRepository.existsById(bookId) || categoryService.getCategoryById(categoryId) == null) {
            throw new ApplicationNotFoundException("Book not found " + bookId + " category " + categoryId);
        }
        Book bookFromRepo = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("This book does not exist in repo"));
        Category category = categoryService.getCategoryById(categoryId);
        bookFromRepo.addCategory(category);
        bookRepository.save(bookFromRepo);
    }

    @Transactional
    public void removeAuthorFromBook(Long bookId, Long authorId) {
        Book bookFromRepo = getBookById(bookId);
        if (bookFromRepo.getAuthors().stream().map(Author::getId).noneMatch(u -> u.equals(authorId))) {
            throw new ApplicationNotFoundException("Author is not attached to this book " + bookId);
        }
        Author author = authorService.getAuthorById(authorId);
        bookFromRepo.removeAuthor(author);
        bookRepository.save(bookFromRepo);
    }

    @Transactional
    public void removeCategoryFromBook(Long bookId, Long categoryId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ApplicationNotFoundException("Book not found " + bookId);
        }
        Book bookFromRepo = getBookById(bookId);
        if (bookFromRepo.getCategories().stream().map(Category::getId).noneMatch(u -> u.equals(categoryId))) {
            throw new ApplicationNotFoundException("Category is not attached to this book " + bookId);
        }
        Category category = categoryService.getCategoryById(categoryId);
        bookFromRepo.removeCategory(category);
        bookRepository.save(bookFromRepo);
    }

    @Transactional
    public void removePublisherFromBook(Long bookId, Long publisherId) {
        if (!bookRepository.existsById(bookId)) {
            throw new ApplicationNotFoundException("Book not found " + bookId);
        }
        Book bookFromRepo = getBookById(bookId);
        if (bookFromRepo.getPublishers().stream().map(Publisher::getId).noneMatch(u -> u.equals(publisherId))) {
            throw new ApplicationNotFoundException("Publisher is not attached to this book " + bookId);
        }
        Publisher publisher = publisherService.getPublisherById(publisherId);
        bookFromRepo.removePublisher(publisher);
        bookRepository.save(bookFromRepo);
    }

    @Transactional
    public void addPublisherToBook(Long bookId, Long publisherId) {
        if (!bookRepository.existsById(bookId) || publisherService.getPublisherById(publisherId) == null) {
            throw new ApplicationNotFoundException("Book not found " + bookId);
        }
        Book bookFromRepo = getBookById(bookId);
        Publisher publisher = publisherService.getPublisherById(publisherId);
        bookFromRepo.addPublisher(publisher);
        bookRepository.save(bookFromRepo);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorID) {
        Author author = authorService.getAuthorById(authorID);
        return bookRepository.getBooksByAuthorsContains(author);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPublisher(Long publisherId) {
        Publisher publisher = publisherService.getPublisherById(publisherId);
        return bookRepository.getBooksByPublishersContains(publisher);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return bookRepository.getBooksByCategoriesContains(category);
    }

    @Transactional(readOnly = true)
    public boolean doesBookExist(Long id) {
        return bookRepository.existsById(id);
    }

    public List<Book> getBooksByParams(Long authorId, Long categoryId, Long publisherId) {
        if (isMoreThanOneParam(authorId, categoryId, publisherId)) {
            throw new ApplicationConflictException("User entered to many params!");
        }
        if (authorId != null) {
            return getBooksByAuthor(authorId);
        }
        if (categoryId != null) {
            return getBooksByCategory(categoryId);
        }
        if (publisherId != null) {
            return getBooksByPublisher(publisherId);
        } else {
            return getAllBooks();
        }
    }

    private boolean isMoreThanOneParam(Long... ids) {
        return Arrays.stream(ids)
                .filter(Objects::nonNull)
                .count() > 1;

    }
}
