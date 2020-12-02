package com.bookshop.controller;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.entity.Author;
import com.bookshop.mapper.AuthorMapper;
import com.bookshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return authorMapper.mapListOfAuthorsToDto(authors);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return authorMapper.mapAuthorEntityToDto(author);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestBody AuthorDto authorDto) {
        authorService.saveAuthor(authorDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAuthor(@RequestBody AuthorDto authorDto) {
        authorService.updateAuthor(authorDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

}
