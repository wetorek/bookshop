package com.bookshop.mapper;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Book;
import com.bookshop.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryDto mapCategoryEntityToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category mapCategoryDtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public Category mapCategoryDtoUsingEntity(CategoryDto categoryDto, Category category) {
        Category mappedCategory = mapCategoryDtoToEntity(categoryDto);
        List<Book> books = category.getBooksCategory();
        category.getBooksCategory().forEach(book -> book.getCategories().remove(category));
        category.setBooksCategory(new LinkedList<>());
        books.forEach(book -> book.addCategory(mappedCategory));
        return mappedCategory;
    }
}
