package com.bookshop.controller;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import com.bookshop.mapper.PublisherMapper;
import com.bookshop.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@AllArgsConstructor
public class PublisherController {

    PublisherService publisherService;
    PublisherMapper publisherMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PublisherDto> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return publisherMapper.mapListToDto(publishers);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherDto getPublisherById(@PathVariable Long id) {
        Publisher publisher = publisherService.getPublisherById(id);
        return publisherMapper.mapPublisherEntityToDto(publisher);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPublisher(@RequestBody PublisherDto publisherDto) {
        publisherService.savePublisher(publisherDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updatePublisher(@RequestBody PublisherDto publisherDto) {
        publisherService.updatePublisher(publisherDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }

}
