package com.bookshop.controller;

import com.bookshop.controller.dto.AuthorDto;
import com.bookshop.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.saveAuthor(authorDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(authorDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }


    //todo delete book from author
    //todo add book to author
}
