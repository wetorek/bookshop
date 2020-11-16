package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.repository.AdditionalServicesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdditionalServicesService {
    private final AdditionalServicesRepository additionalServicesRepository;
    public ResponseEntity<List<AdditionalServiceDto>> getAllServices() {
        List<AdditionalServiceDto> additionalServiceDtoList = additionalServicesRepository.findAll();
        return new ResponseEntity<>()
    }
}
