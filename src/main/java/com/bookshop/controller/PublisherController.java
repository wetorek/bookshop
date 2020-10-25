package com.bookshop.controller;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import com.bookshop.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
@AllArgsConstructor
public class PublisherController {

    PublisherService publisherService;

    @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok().body(publisherService.getAllPublishers());
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPublisher(@RequestBody PublisherDto publisherDto) {
        return publisherService.savePublisher(publisherDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updatePublisher(@RequestBody PublisherDto publisherDto) {
        return publisherService.updatePublisher(publisherDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        return publisherService.deletePublisher(id);
    }

}
