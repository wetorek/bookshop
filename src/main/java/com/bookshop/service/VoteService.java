package com.bookshop.service;

import com.bookshop.controller.dto.VoteDto;
import com.bookshop.entity.Book;
import com.bookshop.entity.User;
import com.bookshop.entity.Vote;
import com.bookshop.exceptions.ApplicationBadRequestException;
import com.bookshop.exceptions.ApplicationConflictException;
import com.bookshop.exceptions.BookNotFoundEx;
import com.bookshop.repository.VoteRepository;
import com.bookshop.service.book.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final BookService bookService;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        if (voteDto.getRating() < 1L || voteDto.getRating() > 10L) {
            throw new ApplicationBadRequestException("Bad request, the vote is out of scale: " + voteDto);
        }
        if (!bookService.doesBookExist(voteDto.getBookId())) {
            throw new BookNotFoundEx("Book not found: " + voteDto.getBookId());
        }
        User user = authService.getCurrentUser();
        if (voteRepository.existsByBookIdAndUserId(voteDto.getBookId(), user.getUserId())) {
            throw new ApplicationConflictException("This user already voted for this book: " + user.getUsername() + "user id: " + user.getUserId() + " " + voteDto);
        }
        voteRepository.save(mapToVote(voteDto, user));
    }

    @Transactional(readOnly = true)
    public List<Vote> getVotesByBook(Book book) {
        return voteRepository.findAllByBookId(book.getId());
    }

    private Vote mapToVote(VoteDto voteDto, User user) {
        return Vote.builder()
                .bookId(voteDto.getBookId())
                .rating(voteDto.getRating())
                .userId(user.getUserId())
                .description(voteDto.getDescription())
                .build();
    }
}
