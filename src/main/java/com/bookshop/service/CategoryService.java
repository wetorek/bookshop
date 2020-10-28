package com.bookshop.service;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Category;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> saveCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        categoryRepository.save(categoryMapper.mapCategoryDtoToEntity(categoryDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Void> updateCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category categoryFromRepo = categoryRepository.findById(categoryDto.getId()).orElseThrow();
        Category newCategory = categoryMapper.mapCategoryDtoUsingEntity(categoryDto, categoryFromRepo);
        categoryRepository.save(newCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Category category = categoryRepository.findById(id).orElseThrow();
        category.getBooksCategory().forEach(book -> book.removeCategory(category));
        categoryRepository.save(category);
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Optional<CategoryDto> categoryDto = categoryRepository.findById(id).map(categoryMapper::mapCategoryEntityToDto);
        return categoryDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
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

    public Optional<Category> getCategoryEntity(Long id) {
        return categoryRepository.findById(id);
    }


}
