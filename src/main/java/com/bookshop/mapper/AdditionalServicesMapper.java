package com.bookshop.mapper;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
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

    public AdditionalServiceDto mapEntityToDto(AdditionalService additionalService) {
        return modelMapper.map(additionalService, AdditionalServiceDto.class);
    }
}
