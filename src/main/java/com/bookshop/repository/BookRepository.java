package com.bookshop.repository;

import com.bookshop.entity.Author;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> getBooksByAuthorsContains(Author author);

    List<Book> getBooksByCategoriesContains(Category category);

    List<Book> getBooksByPublishersContains(Publisher publisher);
}
