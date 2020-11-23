package com.bookshop.controller;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.service.AdditionalServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additional-services")
@AllArgsConstructor
public class AdditionalServicesController {
    private final AdditionalServicesService additionalServicesService;

    @GetMapping
    public ResponseEntity<List<AdditionalServiceDto>> getAll() {
        return additionalServicesService.getAllServices();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addAdditionalService(@RequestBody AdditionalServiceDto additionalServiceDto) {
        return additionalServicesService.addService(additionalServiceDto);
    }
}
