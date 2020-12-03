package com.bookshop.mapper;

import com.bookshop.controller.dto.VoteDto;
import com.bookshop.entity.Vote;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class VoteMapper {

    private final ModelMapper modelMapper;

    public List<VoteDto> mapListOfVotesToDto(List<Vote> voteList) {
        return voteList.stream()
                .map(vote -> modelMapper.map(vote, VoteDto.class))
                .collect(Collectors.toList());
    }
}
