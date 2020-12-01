package com.bookshop.service;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Category;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.ApplicationNotFoundException;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void saveCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            throw new ApplicationConflictException("Category already exists " + categoryDto);
        }
        categoryRepository.save(categoryMapper.mapCategoryDtoToEntity(categoryDto));
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            throw new ApplicationNotFoundException("Category does not exist: " + categoryDto);
        }
        Category categoryFromRepo = getCategoryById(categoryDto.getId());
        Category newCategory = categoryMapper.mapCategoryDtoUsingEntity(categoryDto, categoryFromRepo);
        categoryRepository.save(newCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ApplicationNotFoundException("Category does not exist: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Category does not exist" + id));
    }


    @Transactional(readOnly = true)
    public List<Category> getCategoriesByList(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream()
                .map(u -> categoryRepository.findById(u.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean existAll(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream()
                .map(CategoryDto::getId)
                .allMatch(categoryRepository::existsById);
    }

}
