package com.bookshop.mapper;

import com.bookshop.controller.dto.PublisherDto;
import com.bookshop.entity.Book;
import com.bookshop.entity.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
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
        List<Book> books = publisher.getBooksPublisher();
        publisher.getBooksPublisher().forEach(book -> book.getPublishers().remove(publisher));
        publisher.setBooksPublisher(new LinkedList<>());
        books.forEach(book -> book.addPublisher(mappedPublisher));
        return mappedPublisher;
    }

}
