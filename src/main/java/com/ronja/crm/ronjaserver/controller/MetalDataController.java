package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.MetalDataDto;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.service.MetalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/metals")
public class MetalDataController {

    private final MetalDataService service;
    private final MetalDataMapper mapper;

    public MetalDataController(@Autowired MetalDataService dataService,
                               @Autowired MetalDataMapper mapper) {
        this.service = dataService;
        this.mapper = mapper;
    }

    @GetMapping("/list")
    public List<MetalDataDto> list() {
        return StreamSupport.stream(service.findAll().spliterator(), false)
            .map(mapper::toDto)
            .toList();
    }

    @GetMapping("/exchange")
    public ResponseEntity<MetalDataDto> exchange() {
        MetalData metalData = service.exchange();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(metalData.getId())
            .toUri();

        return ResponseEntity.created(uri).body(mapper.toDto(metalData));
    }
}
