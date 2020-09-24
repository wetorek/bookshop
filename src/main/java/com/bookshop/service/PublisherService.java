package com.bookshop.service;

import com.bookshop.entity.Publisher;
import com.bookshop.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public List<Publisher> getAllPublichers() {
        return publisherRepository.findAll();
    }

    public void savePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void updatePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

}
