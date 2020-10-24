package com.bookshop.service;

import com.bookshop.controller.dto.CategoryDto;
import com.bookshop.entity.Category;
import com.bookshop.mapper.CategoryMapper;
import com.bookshop.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryEntityToDto)
                .collect(Collectors.toList());
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Optional<CategoryDto> categoryDto  = categoryRepository.findById(id).map(categoryMapper::mapCategoryEntityToDto);
        return categoryDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
