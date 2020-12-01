package com.bookshop.controller;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.AdditionalService;
import com.bookshop.mapper.AdditionalServicesMapper;
import com.bookshop.service.AdditionalServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additional-services")
@AllArgsConstructor
public class AdditionalServicesController {
    private final AdditionalServicesService additionalServicesService;
    private final AdditionalServicesMapper additionalServicesMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdditionalServiceDto> getAll() {
        List<AdditionalService> additionalServices = additionalServicesService.getAllServices();
        return additionalServicesMapper.mapListOfEntitiesToDto(additionalServices);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAdditionalService(@RequestBody AdditionalServiceDto additionalServiceDto) {
        additionalServicesService.addService(additionalServiceDto);
    }

    @GetMapping("/{Id}")
    @ResponseStatus(HttpStatus.OK)
    public AdditionalServiceDto get(@PathVariable Long Id) {
        AdditionalService additionalService = additionalServicesService.getById(Id);
        return additionalServicesMapper.mapEntityToDto(additionalService);
    }
}
