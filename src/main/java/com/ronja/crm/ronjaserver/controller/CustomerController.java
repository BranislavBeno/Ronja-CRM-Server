package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerCreationDto;
import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.dto.CustomerIdDto;
import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  CustomerMapper mapper;

  private final EntityService<Customer, CustomerDto> service;

  public CustomerController(@Autowired EntityService<Customer, CustomerDto> service) {
    this.service = service;
  }

  @GetMapping("/list")
  public List<CustomerDto> list() {
    return service.findAll()
        .stream()
        .map(mapper::toDto)
        .toList();
  }

  @PostMapping("/add")
  public ResponseEntity<CustomerIdDto> add(@RequestBody CustomerCreationDto dto) {
    Customer customer = service.save(mapper.toEntity(dto));

    int id = customer.getId();
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();

    CustomerIdDto customerIdDto = new CustomerIdDto(id);
    return ResponseEntity.created(uri).body(customerIdDto);
  }

  @PostMapping("/update")
  public ResponseEntity<CustomerIdDto> update(@RequestBody CustomerDto dto) {
    if (service.existsById(dto.id())) {
      Customer customer = service.save(mapper.toEntity(dto));
      CustomerIdDto customerIdDto = new CustomerIdDto(customer.getId());
      return ResponseEntity.ok(customerIdDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
