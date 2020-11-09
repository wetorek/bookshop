package com.bookshop.service;

import com.bookshop.controller.dto.VoteDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    public ResponseEntity<Void> vote (VoteDto voteDto){

    }
}
