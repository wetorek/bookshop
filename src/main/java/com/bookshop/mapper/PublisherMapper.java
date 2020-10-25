package com.bookshop.mapper;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public class PublisherMapper {
    private final ModelMapper modelMapper;

    public PublisherDto mapPublisherEntityToDto(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public Publisher mapPublisherDtoToEntity(PublisherDto publisherDto) {
        return modelMapper.map(publisherDto, Publisher.class);
    }
}
