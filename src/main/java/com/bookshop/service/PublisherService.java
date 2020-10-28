package com.bookshop.service;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import com.bookshop.mapper.PublisherMapper;
import com.bookshop.repository.PublisherRepository;
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
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Transactional(readOnly = true)
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::mapPublisherEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<Void> savePublisher(PublisherDto publisherDto) {
        if (publisherRepository.existsById(publisherDto.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        publisherRepository.save(publisherMapper.mapPublisherDtoToEntity(publisherDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Void> updatePublisher(PublisherDto publisherDto) {
        if (!publisherRepository.existsById(publisherDto.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Publisher publisherFromRepo = publisherRepository.findById(publisherDto.getId()).orElseThrow();
        Publisher newPublisher = publisherMapper.mapPublisherDtoUsingEntity(publisherDto, publisherFromRepo);
        publisherRepository.save(newPublisher);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Void> deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        publisher.getBooksPublisher().forEach(book -> book.removePublisher(publisher));
        publisherRepository.save(publisher);
        publisherRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PublisherDto> getPublisherById(Long id) {
        Optional<PublisherDto> publisherDto = publisherRepository.findById(id).map(publisherMapper::mapPublisherEntityToDto);
        return publisherDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public boolean allExist(List<PublisherDto> publisherDtoList) {
        return publisherDtoList.stream()
                .map(PublisherDto::getId)
                .allMatch(publisherRepository::existsById);
    }

    public Optional<Publisher> getPublisherEntity(Long id) {
        return publisherRepository.findById(id);
    }

    public List<Publisher> getPublishersByList(List<PublisherDto> publisherDtoList) {
        return publisherDtoList.stream()
                .map(PublisherDto::getId)
                .map(publisherRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
