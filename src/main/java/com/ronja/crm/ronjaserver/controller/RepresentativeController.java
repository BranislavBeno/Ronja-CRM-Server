package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/representatives")
public class RepresentativeController {

  @Autowired
  private RepresentativeMapper mapper;

  @Autowired
  EntityService<Customer> customerService;

  private final EntityService<Representative> service;

  public RepresentativeController(@Autowired EntityService<Representative> service) {
    this.service = service;
  }

  @GetMapping("/list")
  public List<Representative> list() {
    return service.findAll();
  }

  @PostMapping("/add")
  public ResponseEntity<RepresentativeDto> add(@Valid @RequestBody RepresentativeDto dto) {
    Representative representative = provideRepresentative(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(representative.getId())
        .toUri();

    return ResponseEntity.created(uri).body(mapper.toDto(representative));
  }

  @PutMapping("/update")
  public ResponseEntity<RepresentativeDto> update(@Valid @RequestBody RepresentativeDto dto) {
    if (service.existsById(dto.id())) {
      Representative representative = provideRepresentative(dto);
      return ResponseEntity.ok(mapper.toDto(representative));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    if (service.existsById(id)) {
      service.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private Representative provideRepresentative(RepresentativeDto dto) {
    Customer customer = customerService.findById(dto.customerId());
    return service.save(mapper.toEntity(dto, customer));
  }
}
