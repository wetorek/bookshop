package com.bookshop.mapper;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public CategoryDto mapCategoryEntityToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category mapCategoryDtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
