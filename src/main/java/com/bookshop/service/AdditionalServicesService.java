package com.bookshop.service;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import com.bookshop.exceptions.AdditionalServiceConflictEx;
import com.bookshop.exceptions.AdditionalServiceNotFoundEx;
import com.bookshop.mapper.AdditionalServicesMapper;
import com.bookshop.repository.AdditionalServicesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdditionalServicesService {
    private final AdditionalServicesRepository additionalServicesRepository;
    private final AdditionalServicesMapper additionalServicesMapper;

    @Transactional(readOnly = true)
    public List<AdditionalService> getAllServices() {
        return additionalServicesRepository.findAll();
    }

    @Transactional
    public void addService(AdditionalServiceDto additionalServiceDto) {
        if (additionalServicesRepository.existsById(additionalServiceDto.getId())) {
            throw new AdditionalServiceConflictEx("Additional service already exists" + additionalServiceDto);
        }
        AdditionalService additionalService = additionalServicesMapper.mapDtoToEntity(additionalServiceDto);
        additionalServicesRepository.save(additionalService);
    }

    @Transactional(readOnly = true)
    public AdditionalService getById(Long id) {
        return additionalServicesRepository.findById(id).orElseThrow(() ->
                new AdditionalServiceNotFoundEx("Additional Service not found " + id));
    }
}
