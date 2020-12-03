package com.bookshop.mapper;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PublisherMapper {
    private final ModelMapper modelMapper;

    public PublisherDto mapPublisherEntityToDto(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapPublisherDtoToEntity(PublisherDto publisherDto) {
        return modelMapper.map(publisherDto, Publisher.class);
    }

    public Publisher mapPublisherDtoUsingEntity(PublisherDto publisherDto, Publisher publisher) {
        Publisher mappedPublisher = mapPublisherDtoToEntity(publisherDto);
        mappedPublisher.setBooksPublisher(publisher.getBooksPublisher());
        return mappedPublisher;
    }

    public List<PublisherDto> mapListToDto(List<Publisher> publishers) {
        return publishers.stream()
                .map(publisher -> modelMapper.map(publisher, PublisherDto.class))
                .collect(Collectors.toList());
    }

}
