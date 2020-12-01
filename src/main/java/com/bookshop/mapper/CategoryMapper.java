package com.bookshop.mapper;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

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
        mappedCategory.setBooksCategory(category.getBooksCategory());
        return mappedCategory;
    }

    public List<CategoryDto> mapListToDto(List<Category> categories) {
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
