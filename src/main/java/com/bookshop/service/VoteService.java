package com.bookshop.service;

import com.bookshop.controller.dto.VoteDto;
import com.bookshop.entity.User;
import com.bookshop.entity.Vote;
import com.bookshop.repository.BookRepository;
import com.bookshop.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final BookRepository bookRepository;
    private final AuthService authService;

    @Transactional
    public ResponseEntity<Void> vote(VoteDto voteDto) {                         //todo combine votes with book, create bookrequest and response- rate field in book
        if (voteDto.getRating() < 1L || voteDto.getRating() > 10L) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!bookRepository.existsById(voteDto.getBookId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = authService.getCurrentUser();
        if (voteRepository.existsByBookIdAndUserId(voteDto.getBookId(), user.getUserId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        voteRepository.save(mapToVote(voteDto, user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Vote mapToVote(VoteDto voteDto, User user) {
        return Vote.builder()
                .bookId(voteDto.getBookId())
                .rating(voteDto.getRating())
                .userId(user.getUserId())
                .build();
    }
}
