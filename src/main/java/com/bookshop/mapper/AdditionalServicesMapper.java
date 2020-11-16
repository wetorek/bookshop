package com.bookshop.mapper;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AdditionalServicesMapper {
    private final ModelMapper modelMapper;

    public List<AdditionalServiceDto> mapListOfEntitiesToDto(List<AdditionalService> additionalServices) {
        return additionalServices
                .stream()
                .map(additionalService -> modelMapper.map(additionalService, AdditionalServiceDto.class))
                .collect(Collectors.toList());
    }

    public AdditionalService mapDtoToEntity(AdditionalServiceDto additionalServiceDto) {
        return modelMapper.map(additionalServiceDto, AdditionalService.class);
    }
}
