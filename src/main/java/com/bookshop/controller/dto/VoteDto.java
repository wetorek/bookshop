package com.bookshop.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private Long bookId;
    @Min(value = 1L)
    @Max(value = 10L)
    private Long rating;
}
