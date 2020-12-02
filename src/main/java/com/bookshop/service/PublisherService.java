package com.bookshop.service;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.ApplicationNotFoundException;
import com.bookshop.mapper.PublisherMapper;
import com.bookshop.repository.PublisherRepository;
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
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Transactional(readOnly = true)
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Transactional
    public void savePublisher(PublisherDto publisherDto) {
        if (publisherRepository.existsById(publisherDto.getId())) {
            throw new ApplicationConflictException("Publisher already exists " + publisherDto);
        }
        publisherRepository.save(publisherMapper.mapPublisherDtoToEntity(publisherDto));
    }

    @Transactional
    public void updatePublisher(PublisherDto publisherDto) {
        if (!publisherRepository.existsById(publisherDto.getId())) {
            throw new ApplicationNotFoundException("Publisher does not exist: " + publisherDto);
        }
        Publisher publisherFromRepo = getPublisherById(publisherDto.getId());
        Publisher newPublisher = publisherMapper.mapPublisherDtoUsingEntity(publisherDto, publisherFromRepo);
        publisherRepository.save(newPublisher);
    }

    @Transactional
    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new ApplicationNotFoundException("Publisher does not exist: " + id);
        }
        publisherRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException("Publisher not found: " + id));
    }


    public List<Publisher> getPublishersByList(List<PublisherDto> publisherDtoList) {
        return publisherDtoList.stream()
                .map(PublisherDto::getId)
                .map(publisherRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public boolean existAll(List<PublisherDto> list) {
        return list.stream()
                .allMatch(publisherDto -> publisherRepository.existsById(publisherDto.getId()));
    }

}
