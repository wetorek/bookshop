package com.bookshop.service;

import com.bookshop.controller.dto.CategoryDto;
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> update(CategoryDto categoryDto) {
//        categoryRepository.save(categoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> deleteById(Long id) {
//        categoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Optional<CategoryDto> categoryDto = categoryRepository.findById(id).map(categoryMapper::mapCategoryEntityToDto);
        return categoryDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
