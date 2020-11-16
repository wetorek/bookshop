package com.bookshop.controller;

import com.bookshop.controller.dto.AdditionalServiceDto;
import com.bookshop.entity.Publisher;
import com.bookshop.service.AdditionalServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/additional-services")
@AllArgsConstructor
public class AdditionalServicesController {
    private final AdditionalServicesService additionalServicesService;

    @GetMapping("/all")
    public ResponseEntity<List<AdditionalServiceDto>> getAll (){
        return additionalServicesService.getAllServices();
    }
}
