package com.bookshop.service.book.strategy;

import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import com.bookshop.repository.BookRepository;
import com.bookshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetBooksByCategoryStrategy implements GetStrategy {
    private final CategoryService categoryService;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks(Long id) {
        Category category = categoryService.getCategoryById(id);
        return bookRepository.getBooksByCategoriesContains(category);
    }
}
