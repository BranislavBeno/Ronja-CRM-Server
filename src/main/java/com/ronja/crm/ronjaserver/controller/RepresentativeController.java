package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/representatives")
public class RepresentativeController {

  @Autowired
  private RepresentativeMapper mapper;

  @Autowired
  EntityService<Customer, CustomerDto> customerService;

  private final EntityService<Representative, RepresentativeDto> service;

  public RepresentativeController(@Autowired EntityService<Representative, RepresentativeDto> service) {
    this.service = service;
  }

  @GetMapping("/list")
  public List<Representative> representativeList() {
    return service.findAll();
  }

  @GetMapping("/search")
  public List<Representative> search(@RequestParam("lastName") String lastName) {
    return service.searchBy(lastName);
  }

  @PostMapping("/add")
  public ResponseEntity<Representative> add(@RequestBody RepresentativeDto dto) {
    var representative = service.addDto(dto);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(representative.getId())
        .toUri();

    return ResponseEntity.created(uri).body(representative);
  }

  @PutMapping("/update")
  public ResponseEntity<RepresentativeDto> update(@RequestBody RepresentativeDto dto) {
    if (service.existsById(dto.id())) {
      Customer customer = customerService.findById(dto.customerId());
      Representative representative = service.save(mapper.toEntity(dto, customer));
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
}
