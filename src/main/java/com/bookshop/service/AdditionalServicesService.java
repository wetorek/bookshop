package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import com.bookshop.exceptions.AdditionalServiceConflictEx;
import com.bookshop.mapper.AdditionalServicesMapper;
import com.bookshop.repository.AdditionalServicesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdditionalServicesService {
    private final AdditionalServicesRepository additionalServicesRepository;
    private final AdditionalServicesMapper additionalServicesMapper;

    public List<AdditionalService> getAllServices() {
        return additionalServicesRepository.findAll();
    }

    public void addService(AdditionalServiceDto additionalServiceDto) {
        if (additionalServicesRepository.existsById(additionalServiceDto.getId())) {
            throw new AdditionalServiceConflictEx("Additional service already exists" + additionalServiceDto);
        }
        AdditionalService additionalService = additionalServicesMapper.mapDtoToEntity(additionalServiceDto);
        additionalServicesRepository.save(additionalService);
    }

    public Optional<AdditionalService> getById(Long id) {
        return additionalServicesRepository.findById(id);
    }
}
