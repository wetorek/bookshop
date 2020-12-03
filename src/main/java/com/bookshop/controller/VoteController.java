package com.bookshop.controller;

import com.bookshop.controller.dto.VoteDto;
import com.bookshop.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
    }
}
