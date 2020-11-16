package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import com.bookshop.mapper.AdditionalServicesMapper;
import com.bookshop.repository.AdditionalServicesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdditionalServicesService {
    private final AdditionalServicesRepository additionalServicesRepository;
    private final AdditionalServicesMapper additionalServicesMapper;

    public ResponseEntity<List<AdditionalServiceDto>> getAllServices() {
        List<AdditionalServiceDto> additionalServiceDtoList = additionalServicesMapper.mapListOfEntitiesToDto(additionalServicesRepository.findAll());
        return new ResponseEntity<>(additionalServiceDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Void> addService(AdditionalServiceDto additionalServiceDto) {
        if (additionalServicesRepository.existsById(additionalServiceDto.getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        AdditionalService additionalService = additionalServicesMapper.mapDtoToEntity(additionalServiceDto);
        additionalServicesRepository.save(additionalService);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
