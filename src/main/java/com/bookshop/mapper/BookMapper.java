package com.bookshop.mapper;

import com.bookshop.controller.dto.book.BookDto;
import com.bookshop.controller.dto.book.BookResponse;
import com.bookshop.entity.Book;
import com.bookshop.entity.Vote;
import com.bookshop.service.VoteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class BookMapper {
    private final ModelMapper modelMapper;
    private final VoteMapper voteMapper;
    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;
    private final PublisherMapper publisherMapper;

    public BookDto mapBookEntityToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setAuthorDtoList(authorMapper.mapListOfAuthorsToDto(book.getAuthors()));
        bookDto.setCategoryDtoList(categoryMapper.mapListToDto(book.getCategories()));
        bookDto.setPublisherDtoList(publisherMapper.mapListToDto(book.getPublishers()));
        return bookDto;
    }

    public Book mapBookDtoToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    private BookResponse mapEntityToResponse(Book book, VoteService voteService) {
        BookResponse bookResponse = modelMapper.map(book, BookResponse.class);
        bookResponse.setAuthorDtoList(authorMapper.mapListOfAuthorsToDto(book.getAuthors()));
        bookResponse.setCategoryDtoList(categoryMapper.mapListToDto(book.getCategories()));
        bookResponse.setPublisherDtoList(publisherMapper.mapListToDto(book.getPublishers()));
        List<Vote> voteList = voteService.getVotesByBook(book);
        bookResponse.setVoteDtoList(voteMapper.mapListOfVotesToDto(voteList));
        return bookResponse;
    }

    public List<BookResponse> mapListOfEntitiesToResponse(List<Book> bookList, VoteService voteService) {
        return bookList
                .stream()
                .map(book -> mapEntityToResponse(book, voteService))
                .collect(Collectors.toList());
    }
}
